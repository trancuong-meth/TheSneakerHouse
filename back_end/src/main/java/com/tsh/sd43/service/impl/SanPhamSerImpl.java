package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.entity.responce.ProductResponce;
import com.tsh.sd43.repository.ISanPhamRepo;
import com.tsh.sd43.repository.ITheLoaiRepo;
import com.tsh.sd43.service.ISanPhamSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SanPhamSerImpl implements ISanPhamSer {

    @Autowired
    private ISanPhamRepo sanPhamRepo;

    public Page<ProductResponce> getProducts(int pageNo, int pageSize, String key){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamRepo.findPanigation(pageable,
                "%" + key + "%");
    }

    public ArrayList<SanPham> getAll(){
//        get all voucher
        return (ArrayList<SanPham>) sanPhamRepo.findByOrderByNgayTaoDesc();
    }

    public SanPham getById(Long id){
        return sanPhamRepo.findProductById(id).get(0);
    }

    public SanPham add(ProductAddRequest req){
        SanPham e = new SanPham();

        if(!sanPhamRepo.findProductByName(req.getTen()).isEmpty()){
            throw new RuntimeException("The type is already exists");
        }
        e.setTen(req.getTen());
        e.setTrangThai(true);
        e.setMoTa(req.getMoTa());
        e.setIdTheLoai(req.getIdTheLoai());
        e.setIdThuongHieu(req.getIdThuongHieu());
        e.setMa(generateCode());

        return sanPhamRepo.save(e);
    }

    public TheLoai update(TheLoai req){
        return null;
    }

    public String generateCode(){
        // generate code
        String newestCode = sanPhamRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "PRODUCT_" + 0;
        }
        return "PRODUCT_" + (Integer.parseInt(newestCode.substring(8)) + 1);
    }
}
