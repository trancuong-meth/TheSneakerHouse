package com.tsh.sd43.controller;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.request.EmployeeAddRequest;
import com.tsh.sd43.service.impl.NhanVienSerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeRestController {


    @Autowired
    private NhanVienSerImpl employeeSer;

    @GetMapping("/find-all-panigation")
    public ResponseEntity<?> getVouchers(@RequestParam("page")Integer pageNo,
                                         @RequestParam("size")Integer pageSize,
                                         @RequestParam("key")String key,
                                         @RequestParam("trang_thai")String trangThai){
        try{
            return new ResponseEntity<>(employeeSer.getEmployeesWithPanigation(pageNo, pageSize, key, trangThai), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody EmployeeAddRequest req){
        try{
            return new ResponseEntity<>(employeeSer.addEmployee(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-employee/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<>(employeeSer.getEmployeeByID(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeAddRequest req){
        try{
            return new ResponseEntity<>(employeeSer.updateEmployee(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
