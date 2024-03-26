package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.VoucherDetailRequest;
import com.tsh.sd43.service.impl.PhieuGiamGiaChiTietSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher-detail")
@CrossOrigin("*")
public class VoucherDetailRestController {

    @Autowired
    private PhieuGiamGiaChiTietSerImpl phieuGiamGiaChiTietSer;

    @PostMapping("/add-voucher-detail")
    public ResponseEntity<?> addVoucherDetail(@RequestBody VoucherDetailRequest voucherDetailRequest){
        try{
            return new ResponseEntity<>(phieuGiamGiaChiTietSer.addVoucherDetail(voucherDetailRequest), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }
    }
}
