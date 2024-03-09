package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.entity.request.ProductDetailAddRequest;
import com.tsh.sd43.repository.ISanPhamChiTietRepo;
import com.tsh.sd43.service.ISanPhamChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SanPhamChiTietSerImpl implements ISanPhamChiTietSer {

    @Autowired
    private ISanPhamChiTietRepo sanPhamChiTietRepo;

    public Page<SanPhamChiTiet> getProducts(int pageNo, int pageSize, String key, Long id){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamChiTietRepo.findPanigation(pageable, id);
    }

    public ArrayList<SanPhamChiTiet> getAll(){
        return (ArrayList<SanPhamChiTiet>) sanPhamChiTietRepo.findByOrderByNgayTaoDesc();
    }

    public SanPhamChiTiet add(ProductDetailAddRequest req){
        SanPhamChiTiet e = new SanPhamChiTiet();

        e.setIdSanPham(req.getIdSanPham());
        e.setTrangThai(true);
        e.setDonGia(req.getDonGia());
        e.setSoLuongTon(req.getSoLuongTon());
        e.setIdKichCo(req.getIdKichCo());
        e.setIdMauSac(req.getIdMauSac());

        return sanPhamChiTietRepo.save(e);
    }

    public TheLoai update(TheLoai req){
        return null;
    }

    public Page<SanPhamChiTiet> findAllAndPanigation(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamChiTietRepo.findAllAndPanigation(pageable);
    }
}
