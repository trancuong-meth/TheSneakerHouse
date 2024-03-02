package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.request.CustomerAddRequest;
import com.tsh.sd43.repository.IKhachHangRepo;
import com.tsh.sd43.service.IKhachHangSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class KhachHangSerImpl implements IKhachHangSer {

    @Autowired
    private IKhachHangRepo khachHangRepo;

    public Page<KhachHang> getCustomersWithPanigation(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return khachHangRepo.findCustomers(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<KhachHang> getAllCustomers(){
//        get all voucher
        return (ArrayList<KhachHang>) khachHangRepo.findAll();
    }

    public KhachHang getCustomerById(Long id){
        return khachHangRepo.findKhachHangById(id).get(0);
    }

    public KhachHang addCustomer(CustomerAddRequest req){
        // add customer
        KhachHang khachHang = new KhachHang();

        khachHang.setMa(generateCode());
        khachHang.setTen(req.getTen());
        khachHang.setNgaySinh(req.getNgaySinh());
        khachHang.setGioiTinh(req.getGioiTinh());
        khachHang.setSoDienThoai(req.getSoDienThoai());
        khachHang.setCccd(req.getCccd());
        khachHang.setEmail(req.getEmail());
        khachHang.setAvatar(req.getAvatar());
        khachHang.setXa(req.getXa());
        khachHang.setPhuong(req.getPhuong());
        khachHang.setTinh(req.getTinh());
        khachHang.setDiaChi(req.getDiaChi());
        khachHang.setMaXa(req.getMaXa());
        khachHang.setMaPhuong(req.getMaPhuong());
        khachHang.setMaTinh(req.getMaTinh());
        khachHang.setTrangThai(true);

        return khachHangRepo.save(khachHang);
    }


    public String generateCode(){
        // generate code
        String newestCode = khachHangRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "CUSTOMER_" + 0;
        }
        return "CUSTOMER_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

    public KhachHang updateCustomer(KhachHang khachHang){
        KhachHang customer = new KhachHang();
        customer.setCccd(khachHang.getCccd());
        customer.setDiaChi(khachHang.getDiaChi());
        customer.setEmail(khachHang.getEmail());
        customer.setGioiTinh(khachHang.getGioiTinh());
        customer.setMa(khachHang.getMa());
        customer.setMaPhuong(khachHang.getMaPhuong());
        customer.setMaTinh(khachHang.getMaTinh());
        customer.setMaXa(khachHang.getMaXa());
        customer.setNgaySinh(khachHang.getNgaySinh());
        customer.setPhuong(khachHang.getPhuong());
        customer.setSoDienThoai(khachHang.getSoDienThoai());
        customer.setTen(khachHang.getTen());
        customer.setTinh(khachHang.getTinh());
        customer.setAvatar(khachHang.getAvatar());
        customer.setId(khachHang.getId());
        customer.setXa(khachHang.getXa());
        customer.setTrangThai(khachHang.getTrangThai());
        customer.setNgayTao(khachHang.getNgayTao());

        return khachHangRepo.save(customer);
    }

}
