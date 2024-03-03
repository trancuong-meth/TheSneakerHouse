package com.tsh.sd43.controller;

import com.tsh.sd43.entity.KichCo;
import com.tsh.sd43.entity.ThuongHieu;
import com.tsh.sd43.service.impl.KichCoSerImpl;
import com.tsh.sd43.service.impl.ThuongHieuSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("size")
@CrossOrigin("*")
public class SizeRestController {

    @Autowired
    private KichCoSerImpl sizeService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(sizeService.getSizes(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getVouchers(){
        try{
            return new ResponseEntity<>(sizeService.getAllSize(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestParam("size") Integer req){
        try{
            return new ResponseEntity<>(sizeService.addSize(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-brand/{id}")
    public ResponseEntity<?> getVoucherById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(sizeService.getSizeById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> editVoucher(@RequestBody KichCo req){
        try{
            return new ResponseEntity<>(sizeService.updateSize(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
