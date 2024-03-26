package com.tsh.sd43.controller;

import com.tsh.sd43.service.impl.VnPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vnpay")
@CrossOrigin("*")
public class VnPayRestController {

    @Autowired
    private VnPayServiceImpl vnPayService;

    @PostMapping("/payment")
    public String payment(@RequestParam("total")Integer total,
                          @RequestParam("orderInfor")String orderInfor,
                          @RequestParam("orderCode")String orderCode) {
        try{
            return vnPayService.createOrderClient(total, orderInfor, orderCode);
        }catch (Exception ex){
            return ex.getMessage();
        }
    }
}
