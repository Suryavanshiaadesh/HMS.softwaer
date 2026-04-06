package com.ss.payment;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class RazorpayService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    public String createOrder(Double amount) {

        try {

            RazorpayClient razorpay =
                    new RazorpayClient(key, secret);

            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpay.orders.create(orderRequest);
            System.out.println(order.toString());
            return order.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}