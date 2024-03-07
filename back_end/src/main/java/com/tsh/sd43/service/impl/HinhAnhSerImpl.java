package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.HinhAnh;
import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.request.ImageAddRequest;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.repository.IHinhAnhRepo;
import com.tsh.sd43.service.IHinhAnhSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HinhAnhSerImpl implements IHinhAnhSer {

    @Autowired
    private IHinhAnhRepo hinhAnhRepo;

    public Page<HinhAnh> getImages(int pageNo, int pageSize, String key){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return hinhAnhRepo.findPanigation(pageable);
    }

    public ArrayList<HinhAnh> getAll(){
//        get all voucher
        return (ArrayList<HinhAnh>) hinhAnhRepo.findByOrderByNgayTaoDesc();
    }

    public HinhAnh getById(Long id){
        return hinhAnhRepo.findImageById(id).get(0);
    }

    public HinhAnh add(ImageAddRequest req){
        HinhAnh e = new HinhAnh();

        e.setTrangThai(true);
        e.setIdSanPhamChiTiet(req.getIdSanPhamChiTiet());
        e.setDuongDan(req.getDuongDan());

        return hinhAnhRepo.save(e);
    }

    public TheLoai update(TheLoai req){
        return null;
    }

}
