package com.tsh.sd43.controller;

import com.tsh.sd43.entity.request.HistoryRequest;
import com.tsh.sd43.service.impl.LichSuSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin("*")
public class HistoryRestController {

    @Autowired
    private LichSuSerImpl lichSuService;
    @GetMapping("/get-all-by-id/{id}")
    public ResponseEntity<?> getAllHistory(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(lichSuService.getAllLichSu(id), HttpStatus.OK);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody HistoryRequest history){
        try{
            return new ResponseEntity<>(lichSuService.addHistory(history), HttpStatus.OK);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
