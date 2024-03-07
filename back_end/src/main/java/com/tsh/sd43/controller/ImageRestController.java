package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.ImageAddRequest;
import com.tsh.sd43.service.impl.HinhAnhSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class ImageRestController {

    @Autowired
    private HinhAnhSerImpl hinhAnhService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getImages(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(hinhAnhService.getImages(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getImages(){
        try{
            return new ResponseEntity<>(hinhAnhService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody ImageAddRequest req){
        try{
            return new ResponseEntity<>(hinhAnhService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
