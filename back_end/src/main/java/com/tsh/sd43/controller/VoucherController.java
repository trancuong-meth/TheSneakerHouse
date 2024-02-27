package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.service.impl.VoucherSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
@CrossOrigin(origins = "*")
public class VoucherController {

    @Autowired
    private VoucherSerImpl voucherService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(voucherService.getVouchers(0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody VoucherAddRequest req){
        try{
            return new ResponseEntity<>(voucherService.addVoucher(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
