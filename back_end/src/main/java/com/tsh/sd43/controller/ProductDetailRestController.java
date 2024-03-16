package com.tsh.sd43.controller;

import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.request.ProductDetailAddRequest;
import com.tsh.sd43.entity.request.ProductDetailSearchRequest;
import com.tsh.sd43.service.impl.SanPhamChiTietSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-detail")
@CrossOrigin("*")
public class ProductDetailRestController {

    @Autowired
    private SanPhamChiTietSerImpl sanPhamChiTietService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page") Integer pageNo,
                                         @RequestParam("size") Integer pageSize,
                                         @RequestParam("key") String key,
                                         @RequestParam("trang_thai") String trangThai,
                                         @RequestParam("id") Long id) {
        try {
            return new ResponseEntity<>(sanPhamChiTietService.getProducts(pageNo, pageSize, key, id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody ProductDetailAddRequest req) {
        try {
            return new ResponseEntity<>(sanPhamChiTietService.add(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam("page") Integer pageNo,
                                    @RequestParam("size") Integer pageSize
                                    ) {
        try {
            return new ResponseEntity<>(sanPhamChiTietService.findAllAndPanigation(pageNo, pageSize), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get-product-detail-sale")
    public ResponseEntity<?> getVoucherByIdString(@RequestBody ProductDetailSearchRequest req){
        try{
            return new ResponseEntity<>(sanPhamChiTietService.getByIdString(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-sale")
    public ResponseEntity<?> addSale(@RequestBody SanPhamChiTiet req) {
        try {
            return new ResponseEntity<>(sanPhamChiTietService.addSale(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-product-detail-by-id-sale/{id}")
    public ResponseEntity<?> getProductDetalByIdSale(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(sanPhamChiTietService.getProductDetailByIDSale(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
