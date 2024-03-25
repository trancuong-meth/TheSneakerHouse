package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.CartDetailRequest;
import com.tsh.sd43.service.impl.GioHangSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartRestController {

    @Autowired
    private GioHangSerImpl cartService;

    @GetMapping("/get-quantity-by-id-customer/{id}")
    public ResponseEntity<?> getQuantityOfCartDetailByIdCustomer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cartService.getQuantityOfCartDetailByIdCustomer(id), HttpStatus.OK);
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody CartDetailRequest req) {
        try{
            return new ResponseEntity<>(cartService.addProductToCart(req), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-cart-detail-by-id/{id}")
    public ResponseEntity<?> findCartDetailsByIdCart(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(cartService.findCartDetailsByIdCart(id), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
