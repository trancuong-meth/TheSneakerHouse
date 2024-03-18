package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.DotGiamGia;
import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.entity.request.ProductDetailAddRequest;
import com.tsh.sd43.entity.request.ProductDetailSearchRequest;
import com.tsh.sd43.repository.ISanPhamChiTietRepo;
import com.tsh.sd43.service.ISanPhamChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//        e.setQRcode(req.getQRcode());

        return sanPhamChiTietRepo.save(e);
    }

    public SanPhamChiTiet update(SanPhamChiTiet req){
        return sanPhamChiTietRepo.save(req);
    }

    public Page<SanPhamChiTiet> findAllAndPanigation(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamChiTietRepo.findAllAndPanigation(pageable);
    }

    public Page<SanPhamChiTiet> getByIdString(ProductDetailSearchRequest req){
        Pageable pageable = PageRequest.of(req.getPageNo(), req.getPageSize());
        List<Integer> listId = List.of(req.getIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());;

        return sanPhamChiTietRepo.getProductDetailByIds(listId,
                req.getIdMauSac() == null ? "%%" : "%" + req.getIdMauSac() + "%",
                req.getIdKichCo() == null ? "%%" : "%" + req.getIdKichCo() + "%",
                req.getIdTheLoai() == null ? "%%" : "%" + req.getIdTheLoai() + "%",
                req.getKey() == null ? "%%" : "%" + req.getKey() + "%",
                pageable);
    }

    public SanPhamChiTiet addSale(SanPhamChiTiet req){
        return sanPhamChiTietRepo.save(req);
    }

    public ArrayList<SanPhamChiTiet> getProductDetailByIDSale(Long id){
        try{
            return sanPhamChiTietRepo.getProductDetailByIdSale(id);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
