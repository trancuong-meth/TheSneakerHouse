package com.tsh.sd43.controller;

import com.tsh.sd43.entity.ThuongHieu;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.service.impl.ThuongHieuSerImpl;
import com.tsh.sd43.service.impl.VoucherSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
public class BrandRestController {

    @Autowired
    private ThuongHieuSerImpl thuongHieuService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(thuongHieuService.getBrands(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(thuongHieuService.getAllBrands(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody String req){
        try{
            return new ResponseEntity<>(thuongHieuService.addBrand(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-brand/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(thuongHieuService.getBrandById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> editVoucher(@RequestBody ThuongHieu req){
        try{
            return new ResponseEntity<>(thuongHieuService.updateBrand(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
