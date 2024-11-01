package org.amazin.ecommerce.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.amazin.ecommerce.customer.CustomerClient;
import org.amazin.ecommerce.exception.BusinessException;
import org.amazin.ecommerce.kafka.OrderConfirmation;
import org.amazin.ecommerce.kafka.OrderProducer;
import org.amazin.ecommerce.orderline.OrderLineRequest;
import org.amazin.ecommerce.orderline.OrderLineService;
import org.amazin.ecommerce.payment.PaymentClient;
import org.amazin.ecommerce.payment.PaymentRequest;
import org.amazin.ecommerce.product.ProductClient;
import org.amazin.ecommerce.product.PurchaseRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No Customer exist with the ID:: " + request.customerId()));

        var purchaseProducts = this.productClient.purchaseProducts(request.products());

        var order = this.orderRepository.save(orderMapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                request.reference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new BusinessException("Cannot find order:: No Order exist with ID:: " + orderId));
    }
}
