package com.tsh.sd43.controller;

import com.tsh.sd43.infrastructure.configs.VNPay.VNPayService;
import com.tsh.sd43.service.impl.VnPayServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class VnPayRestController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/payment")
    public String payment(@RequestParam("total")Integer total,
                          @RequestParam("orderInfor")String orderInfor,
                          @RequestParam("currentLocation")String currentLocation,
                          HttpSession session) {
        try{
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            return vnPayService.createOrder(total, orderInfor, baseUrl);
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

}
