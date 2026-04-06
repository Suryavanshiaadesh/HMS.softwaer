package com.ss.payment;

import java.time.LocalDateTime;

import com.ss.student.Students;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(
            name = "payment_seq",
            sequenceName = "payments_seq",
            allocationSize = 1
    )
    private Long id;   // Database Primary Key

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;   // Razorpay Order ID

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId; // Razorpay Payment ID

    @Column(name = "razorpay_signature")
    private String signature; // Razorpay Signature

    private Double amount;

    private String status; // SUCCESS / FAILED / CANCELLED

    private LocalDateTime paymentTime;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Students student;
}