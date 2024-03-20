package com.tsh.sd43.controller;

import com.tsh.sd43.entity.HoaDonChiTiet;
import com.tsh.sd43.entity.request.ProductDetailRequest;
import com.tsh.sd43.service.impl.HoaDonChiTietSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill-detail")
@CrossOrigin("*")
public class BillDetailRestController {

    @Autowired
    private HoaDonChiTietSerImpl hoaDonChiTietService;

    @GetMapping("/get-by-bill")
    public ResponseEntity<?> getByProduct(@RequestParam("id") Long id,
                                          @RequestParam("page")Integer pageNo,
                                          @RequestParam("size")Integer pageSize) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.findProductDetailsByIdProduct(pageNo, pageSize,id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-product-to-bill")
    public ResponseEntity<?> addToBill(@RequestBody ProductDetailRequest req) {
        try {
            return new ResponseEntity<>(hoaDonChiTietService.addProductToBill(req), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove-by-id/{id}")
    public void deleteProductDetailByID(@PathVariable("id")Long id){
        try{
            hoaDonChiTietService.removeBillDetailById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/refund-single")
    public void deleteProductDetailByID(@RequestBody HoaDonChiTiet req){
        try{
            hoaDonChiTietService.updateBillDetail(req);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
