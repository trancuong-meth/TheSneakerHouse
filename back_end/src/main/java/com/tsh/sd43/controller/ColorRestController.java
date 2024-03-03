package com.tsh.sd43.controller;

import com.tsh.sd43.entity.MauSac;
import com.tsh.sd43.service.impl.MauSacSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/color")
@CrossOrigin("*")
public class ColorRestController {

    @Autowired
    private MauSacSerImpl mauSacService;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getColors(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(mauSacService.getColors(pageNo, pageSize, key), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getColors(){
        try{
            return new ResponseEntity<>(mauSacService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addColor(@RequestParam("name") String req){
        try{
            return new ResponseEntity<>(mauSacService.add(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-color/{id}")
    public ResponseEntity<?> getColorById(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(mauSacService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> editColor(@RequestBody MauSac req){
        try{
            return new ResponseEntity<>(mauSacService.update(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
