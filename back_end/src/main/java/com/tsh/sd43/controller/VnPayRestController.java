package com.tsh.sd43.controller;

import com.tsh.sd43.infrastructure.configs.VNPay.VNPayService;
import com.tsh.sd43.service.impl.VnPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class VnPayRestController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpServletRequest request;

    public String currentLocation = "";

    @PostMapping("/payment")
    public String payment(@RequestParam("total")Integer total,
                          @RequestParam("orderInfor")String orderInfor,
                          @RequestParam("currentLocation")String currentLocation) {
        try{
//            currentLocation = currentLocation;
//            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            return vnPayService.createOrder(total, orderInfor, currentLocation);
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @GetMapping("/vnpay-payment")
    public String redirectVnpayToSuccess(){
        int paymentStatus =vnPayService.orderReturn(request);

        System.out.println(currentLocation);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        return paymentStatus == 1 ? "ordersuccess" : "orderfail";
    }
}
