package com.tsh.sd43.controller.controller;

import com.tsh.sd43.infrastructure.configs.VNPay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/vnpay-payment")
    public String getHee(){

        int paymentStatus =vnPayService.orderReturn(request);


        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        return paymentStatus == 1 ? "index" : "index2";
    }
}
