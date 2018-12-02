package com.example.snack.order;

import com.example.snack.exception.MessageException;
import com.example.snack.order.model.Order;
import com.example.snack.order.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/${api.version}")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllOrders(@PageableDefault(size = 1000) Pageable pageable) {
        Page<Order> orders = orderService.getAllOrders(pageable);
        if (orders.hasContent()) {
            return ResponseEntity.ok(orders.getContent());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrder(@Validated @RequestBody OrderData orderData) {
        if ((orderData.getSnacks() == null || orderData.getSnacks().isEmpty()) &&
                (orderData.getCustomSnacks() == null || orderData.getCustomSnacks().isEmpty())) {
            throw new MessageException("At least one snack or custom snack is required.", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(orderService.createOrder(orderData));
    }
}