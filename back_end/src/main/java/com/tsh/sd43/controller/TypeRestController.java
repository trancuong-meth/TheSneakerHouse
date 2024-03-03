package com.tsh.sd43.controller;

import com.tsh.sd43.entity.MauSac;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.service.impl.MauSacSerImpl;
import com.tsh.sd43.service.impl.TheLoaiSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type")
@CrossOrigin("*")
public class TypeRestController {

    @Autowired
    private TheLoaiSerImpl theLoaiService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getTypes(@RequestParam("page")Integer pageNo,
                                       @RequestParam("size")Integer pageSize,
                                       @RequestParam("key")String key,
                                       @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(theLoaiService.getTypes(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllType(){
        try{
            return new ResponseEntity<>(theLoaiService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestParam("name") String req){
        try{
            return new ResponseEntity<>(theLoaiService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-type/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(theLoaiService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> editType(@RequestBody TheLoai req){
        try{
            return new ResponseEntity<>(theLoaiService.update(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
