package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.DotGiamGia;
import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.request.ProductAddRequest;
import com.tsh.sd43.entity.request.ProductDetailAddRequest;
import com.tsh.sd43.entity.request.ProductDetailSearchRequest;
import com.tsh.sd43.entity.responce.ColorIdentityResponse;
import com.tsh.sd43.entity.responce.ProductBestSellerResponse;
import com.tsh.sd43.entity.responce.ProductDetailIdentityReponse;
import com.tsh.sd43.repository.*;
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

    @Autowired
    private IHoaDonRepo hoaDonRepo;

    @Autowired
    private IMauSacRepo mauSacRepo;

    @Autowired
    private IHinhAnhRepo hinhanhRepo;

    public Page<SanPhamChiTiet> getProducts(int pageNo, int pageSize,
                                            Long id,
                                            String idKichCo, String idMauSac){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return sanPhamChiTietRepo.findPanigation(pageable, id,
                                        "%" + idKichCo + "%",
                                        "%" + idMauSac + "%");
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

    public ArrayList<SanPhamChiTiet> getProductDetailNewest(){
        ArrayList<SanPhamChiTiet> productDetails = (ArrayList<SanPhamChiTiet>) sanPhamChiTietRepo.findAll();

        ArrayList<SanPhamChiTiet> productDetailNewest = new ArrayList<>();
        for (SanPhamChiTiet sp : productDetails) {
            if(sp.getIdSanPham().getTrangThai()){
                Boolean flag = false;
                if(productDetailNewest.isEmpty()){
                    productDetailNewest.add(sp);
                    continue;
                }
                for(SanPhamChiTiet sp1 : productDetailNewest){
                    if(sp1.getIdSanPham().getMa().equals(sp.getIdSanPham().getMa())){
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    productDetailNewest.add(sp);
                }
            }

        }
        return productDetailNewest;
    }

    public ArrayList<SanPhamChiTiet> getTopProductBestSeller(){
        ArrayList<ProductBestSellerResponse> resp = hoaDonRepo.getTopProductBestSeller();
        ArrayList<SanPhamChiTiet> productDetails = (ArrayList<SanPhamChiTiet>) sanPhamChiTietRepo.findAll();

        ArrayList<SanPhamChiTiet> productDetailNewest = new ArrayList<>();
        for (ProductBestSellerResponse sp : resp) {
            for(SanPhamChiTiet sp1 : productDetails){
                if(sp1.getIdSanPham().getTrangThai()){
                    if(sp1.getIdSanPham().getId().equals(sp.getId())){
                        productDetailNewest.add(sp1);
                        break;
                    }
                }
            }
        }
        return productDetailNewest;
    }

    public ArrayList<SanPhamChiTiet> getProductDetailWithSales(){
        ArrayList<SanPhamChiTiet> productDetailsWithSale = sanPhamChiTietRepo.getTopProductOfSales();
        ArrayList<SanPhamChiTiet> productDetails = (ArrayList<SanPhamChiTiet>) sanPhamChiTietRepo.findAll();

        ArrayList<SanPhamChiTiet> productDetailNewest = new ArrayList<>();
        for (SanPhamChiTiet spct : productDetailsWithSale) {
            for(SanPhamChiTiet sp1 : productDetails){
                if(sp1.getIdSanPham().getTrangThai()){
                    if(sp1.getId().equals(spct.getId())){
                        productDetailNewest.add(sp1);
                        break;
                    }
                }
            }
        }
        return productDetailNewest;
    }


    public ArrayList<ProductDetailIdentityReponse> getProductDetailIdentity(Long id){
        ArrayList<ColorIdentityResponse> colors = mauSacRepo.getColorIdentity(id);

        ArrayList<ProductDetailIdentityReponse> resp = new ArrayList<>();
        for (ColorIdentityResponse color : colors) {
            ProductDetailIdentityReponse e = new ProductDetailIdentityReponse();
            e.setId(color.getId());
            e.setTen(color.getTen());
            e.setDuongDan(hinhanhRepo.getPathImageByIdColorAndIdProduct(color.getId(), id));
            resp.add(e);
        }
        return resp;
    }

}
