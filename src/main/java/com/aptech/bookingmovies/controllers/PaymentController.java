package com.aptech.bookingmovies.controllers;

import com.aptech.bookingmovies.services.payment.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${api.prefix}/paypal")
@RequiredArgsConstructor

public class PaymentController {

    @Autowired
    private final PayPalService payPalService;

    @PostMapping("/pay")
    public String pay(@RequestParam("sum") double sum) {
        try {
            Payment payment = payPalService.createPayment(
                    sum,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment description",
                    "http://localhost:8081/api/paypal/cancel",
                    "http://localhost:8081/api/paypal/success");
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "Payment canceled";
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Payment successful";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "Payment failed";
    }
}

