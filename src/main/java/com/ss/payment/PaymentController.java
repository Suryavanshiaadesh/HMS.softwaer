package com.ss.payment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;

import com.ss.student.StudentRepo;
import com.ss.student.Students;
import com.razorpay.Utils;

@RestController
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Value("${razorpay.secret}")
    private String secret;

    // Create order
    @PostMapping("/createOrder")
    public String createOrder(
            @RequestParam Long studentId,
            @RequestParam Double amount
    ) {
    	System.out.println("order is created"+studentId+"==========="+amount);
        return razorpayService.createOrder(amount);
    }

    // Verify payment

    @Transactional
    @PostMapping("/verify")
    public boolean verifyPayment(

            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature,
            @RequestParam Long studentId,
            @RequestParam Double amount,
            @RequestParam String status
    ) {

        try {

            Optional<Students> studentOpt =
                    studentRepo.findById(studentId);

            if (studentOpt.isEmpty()) return false;

            Students student = studentOpt.get();

            System.out.println("Student ID: " + studentId);
            System.out.println("Old Due Date: " + student.getDueDate());

            Payment payment = new Payment();

            payment.setRazorpayOrderId(orderId);
            payment.setRazorpayPaymentId(paymentId);
            payment.setSignature(signature);
            payment.setAmount(amount);
            payment.setStudent(student);
            payment.setPaymentTime(LocalDateTime.now());

            if (status.equals("SUCCESS")) {

                JSONObject options = new JSONObject();

                options.put("razorpay_order_id", orderId);
                options.put("razorpay_payment_id", paymentId);
                options.put("razorpay_signature", signature);

                boolean isValid =
                        Utils.verifyPaymentSignature(options, secret);

                System.out.println("Signature Valid: " + isValid);

                if (isValid) {

                    payment.setStatus("SUCCESS");

                    LocalDate dueDate = student.getDueDate();

                    if (dueDate == null) {

                        dueDate = LocalDate.now();
                    }

                    LocalDate newDueDate = dueDate.plusMonths(1);

                    student.setDueDate(newDueDate);

                    studentRepo.saveAndFlush(student);

                    System.out.println("New Due Date: " + newDueDate);


                } else {

                    payment.setStatus("FAILED");
                }

            } else {

                payment.setStatus(status);
            }

            paymentRepo.save(payment);

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }
}