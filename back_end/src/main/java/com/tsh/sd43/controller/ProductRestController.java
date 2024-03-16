package com.tsh.sd43.controller;

import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.service.impl.SanPhamSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductRestController {

    @Autowired
    private SanPhamSerImpl sanPhamService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(sanPhamService.getProducts(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(sanPhamService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody ProductAddRequest req){
        try{
            return new ResponseEntity<>(sanPhamService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(sanPhamService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
