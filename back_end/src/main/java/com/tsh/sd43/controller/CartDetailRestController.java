package com.tsh.sd43.controller;

import com.tsh.sd43.service.impl.GioHangChiTietSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-detail")
@CrossOrigin("*")
public class CartDetailRestController {

    @Autowired
    private GioHangChiTietSerImpl gioHangChiTietService;

    @DeleteMapping("/remove-cart-detail/{id}")
    public ResponseEntity<?> removeCartDetail(@PathVariable("id") Long id) {
        try {
            gioHangChiTietService.removeCartDetailById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
