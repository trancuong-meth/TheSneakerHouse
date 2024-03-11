package com.tsh.sd43.controller;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.request.ProductVoucherUpdateRequest;
import com.tsh.sd43.service.impl.HoaDonSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/bill")
@CrossOrigin("*")
public class BillRestController {

    @Autowired
    private HoaDonSerImpl hoaDonService;

    @GetMapping("/get-bill-by-state/{state}")
    public ArrayList<HoaDon> getHoaDonByTrangThai(@PathVariable("state") Integer trangThai) {
        return hoaDonService.getHoaDonByTrangThai(trangThai);
    }

    @PostMapping("/create-bill-wait")
    public HoaDon createHoaDon() {
        return hoaDonService.createBillWait();
    }

    @DeleteMapping("/delete-bill/{id}")
    public ResponseEntity<?> deleteHoaDon(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(hoaDonService.deleteBillById(id), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/add-voucher-to-bill")
    public ResponseEntity<?> addVoucherToBill(@RequestBody ProductVoucherUpdateRequest req) {
        try{
            return new ResponseEntity<>(hoaDonService.addVoucherToBill(req), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-bill")
    public ResponseEntity<?> updateHoaDon(@RequestBody HoaDon hoaDon) {
        try{
            return new ResponseEntity<>(hoaDonService.updateHoaDon(hoaDon), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-voucher-by-id/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(hoaDonService.getVoucherByIdBill(id), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-customer-by-id/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id) {
        try{
            return new ResponseEntity<>(hoaDonService.getCustomerByIdBill(id), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
