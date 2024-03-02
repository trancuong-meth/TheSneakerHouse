package com.tsh.sd43.controller;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.request.CustomerAddRequest;
import com.tsh.sd43.service.impl.KhachHangSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerRestController {

        @Autowired
        private KhachHangSerImpl customerSer;

        @GetMapping("/find-all-panigation")
        public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                             @RequestParam("size")Integer pageSize,
                                             @RequestParam("key")String key,
                                             @RequestParam("trang_thai")String trangThai){
            try{
                return new ResponseEntity<>(customerSer.getCustomersWithPanigation(pageNo, pageSize, key, trangThai), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody CustomerAddRequest req){
        try{
            return new ResponseEntity<>(customerSer.addCustomer(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(customerSer.getCustomerById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody KhachHang khachHang){
        try{
         return new ResponseEntity<>(customerSer.updateCustomer(khachHang), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
