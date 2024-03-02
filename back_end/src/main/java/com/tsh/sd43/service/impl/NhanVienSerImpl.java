package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.ChucVu;
import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.NhanVien;
import com.tsh.sd43.entity.request.EmployeeAddRequest;
import com.tsh.sd43.repository.IChucVuRepo;
import com.tsh.sd43.repository.INhanVienRepo;
import com.tsh.sd43.service.INhanVienSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NhanVienSerImpl implements INhanVienSer {

    @Autowired
    private INhanVienRepo nhanVienRepo;

    @Autowired
    private IChucVuRepo chucVuRepo;

    public Page<NhanVien> getEmployeesWithPanigation(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return nhanVienRepo.findEmployees(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<NhanVien> getAllCustomers(){
//        get all voucher
        return (ArrayList<NhanVien>) nhanVienRepo.findAll();
    }

    public NhanVien getEmployeeByID(Long id){
        return nhanVienRepo.findEmployeeByID(id).get(0);
    }

    public NhanVien addEmployee(EmployeeAddRequest req){
        NhanVien employee = new NhanVien();

        employee.setCccd(req.getCccd());
        employee.setDiaChi(req.getDiaChi());
        employee.setEmail(req.getEmail());
        employee.setGioiTinh(req.getGioiTinh());
        employee.setMa(generateCode());
        employee.setMaPhuong(req.getMaPhuong());
        employee.setMaTinh(req.getMaTinh());
        employee.setMaXa(req.getMaXa());
        employee.setXa(req.getXa());
        employee.setPhuong(req.getPhuong());
        employee.setTinh(req.getTinh());
        employee.setNgaySinh(req.getNgaySinh());
        employee.setSoDienThoai(req.getSoDienThoai());
        employee.setTrangThai(true);
        employee.setTen(req.getTen());
        employee.setAvatar(req.getAvatar());

        if(req.getChucVu() == 0){
            if(chucVuRepo.findByMa("0") != null){
                employee.setChucVu(chucVuRepo.findByMa("0"));
            }else{
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("0");
                chucVu.setTen("Nhân viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        }else if(req.getChucVu() == 1){
            if(chucVuRepo.findByMa("1") != null){
                employee.setChucVu(chucVuRepo.findByMa("1"));
            }else{
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("1");
                chucVu.setTen("Quản trị viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        }

        return nhanVienRepo.save(employee);
    }

    public String generateCode(){
        // generate code
        String newestCode = nhanVienRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "CUSTOMER_" + 0;
        }
        return "CUSTOMER_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

    public NhanVien updateEmployee(EmployeeAddRequest req){
        NhanVien employee = new NhanVien();

        employee.setCccd(req.getCccd());
        employee.setDiaChi(req.getDiaChi());
        employee.setEmail(req.getEmail());
        employee.setGioiTinh(req.getGioiTinh());
        employee.setMa(req.getMa());
        employee.setMaPhuong(req.getMaPhuong());
        employee.setMaTinh(req.getMaTinh());
        employee.setMaXa(req.getMaXa());
        employee.setXa(req.getXa());
        employee.setPhuong(req.getPhuong());
        employee.setTinh(req.getTinh());
        employee.setNgaySinh(req.getNgaySinh());
        employee.setSoDienThoai(req.getSoDienThoai());
        employee.setTrangThai(true);
        employee.setTen(req.getTen());
        employee.setAvatar(req.getAvatar());
        employee.setId(req.getId());

        if(req.getChucVu() == 0){
            if(chucVuRepo.findByMa("0") != null){
                employee.setChucVu(chucVuRepo.findByMa("0"));
            }else{
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("0");
                chucVu.setTen("Nhân viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        }else if(req.getChucVu() == 1){
            if(chucVuRepo.findByMa("1") != null){
                employee.setChucVu(chucVuRepo.findByMa("1"));
            }else{
                ChucVu chucVu = new ChucVu();
                chucVu.setMa("1");
                chucVu.setTen("Quản trị viên");
                chucVuRepo.save(chucVu);
                employee.setChucVu(chucVu);
            }
        }

        return nhanVienRepo.save(employee);
    }

}
