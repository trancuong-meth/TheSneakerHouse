package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.PaymentMethodAddRequest;
import com.tsh.sd43.service.impl.HinhThucThanhToanSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-method")
@CrossOrigin("*")
public class PaymentMethodRestController {

    @Autowired
    private HinhThucThanhToanSerImpl paymentMethodSer;

    @PostMapping("/add")
    public ResponseEntity<?> addPaymentMethod(@RequestBody PaymentMethodAddRequest req){
        try{
            return new ResponseEntity<>(paymentMethodSer.addPaymentMethod(req), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all/{id}")
    public ResponseEntity<?> getALlPaymentMethod(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(paymentMethodSer.getAllPaymentMethodByIdBill(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}
