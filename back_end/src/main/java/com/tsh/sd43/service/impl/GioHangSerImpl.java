package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.GioHang;
import com.tsh.sd43.entity.GioHangChiTiet;
import com.tsh.sd43.entity.HoaDonChiTiet;
import com.tsh.sd43.entity.request.CartDetailRequest;
import com.tsh.sd43.entity.request.ProductDetailRequest;
import com.tsh.sd43.repository.IGioHangChiTietRepo;
import com.tsh.sd43.repository.IGioHangRepo;
import com.tsh.sd43.repository.IKhachHangRepo;
import com.tsh.sd43.service.IGioHangSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GioHangSerImpl implements IGioHangSer {

    @Autowired
    private IGioHangRepo gioHangRepo;

    @Autowired
    private IGioHangChiTietRepo gioHangChiTietRepo;

    @Autowired
    private IKhachHangRepo khachHangRepo;

    public GioHang getCartByIdCustomer(Long id) {
        GioHang cart = new GioHang();
        if(id == -1){
            if(gioHangRepo.getGioHangKhongDangNhap().size() != 0){
                cart = gioHangRepo.getGioHangKhongDangNhap().get(0);
            }else{
               return gioHangRepo.save(cart);
            }
        }else{
            if(gioHangRepo.getCartByIdKhachHang(id).size() != 0){
                cart = gioHangRepo.getCartByIdKhachHang(id).get(0);
            }else{
                cart.setIdKhachHang(khachHangRepo.findById(id).get());
                return gioHangRepo.save(cart);
            }
        }
        return cart;
    }

    public Long getQuantityOfCartDetailByIdCustomer(Long id) {
        GioHang cart = getCartByIdCustomer(id);
        return gioHangChiTietRepo.getQuantityOfCartDetailByIdCart(cart.getId()) == null ? 0 : gioHangChiTietRepo.getQuantityOfCartDetailByIdCart(cart.getId());
    }

    public GioHangChiTiet addProductToCart(CartDetailRequest req) {
        GioHang cart = getCartByIdCustomer(req.getIdKhachHang());
        try {
            ArrayList<GioHangChiTiet> productDetails = gioHangChiTietRepo.findCartDetailsByIdCart(cart.getId());

            if(productDetails.size() == 0) {
                GioHangChiTiet cartDetail = new GioHangChiTiet();
                cartDetail.setIdGioHang(cart);
                cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                cartDetail.setSoLuong(1);
                return gioHangChiTietRepo.save(cartDetail);
            }else{
                for(GioHangChiTiet item : productDetails) {
                    if(item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {

                        if(req.getSoLuong() == -1) {
                            if(item.getSoLuong() + 1 > req.getSanPhamChiTiet().getSoLuongTon()) {
                                throw new RuntimeException("Số lượng còn lại không đủ");
                            }

                            item.setSoLuong(item.getSoLuong() + 1);
                            return gioHangChiTietRepo.save(item);
                        }else{
                            if(req.getSanPhamChiTiet().getSoLuongTon() >= req.getSoLuong()) {
                                item.setSoLuong(req.getSoLuong());
                                return gioHangChiTietRepo.save(item);
                            }else{
                                throw new RuntimeException("Số lượng tồn không đủ");
                            }
                        }
                    }
                }

                if(req.getSoLuong() == -1) {
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(1);
                    return gioHangChiTietRepo.save(cartDetail);
                }else{
                    GioHangChiTiet cartDetail = new GioHangChiTiet();
                    cartDetail.setIdGioHang(cart);
                    cartDetail.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    cartDetail.setSoLuong(req.getSoLuong());
                    return gioHangChiTietRepo.save(cartDetail);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<GioHangChiTiet> findCartDetailsByIdCart(Long id) {
        GioHang cart = getCartByIdCustomer(id);
        return gioHangChiTietRepo.findCartDetailsByIdCart(cart.getId());
    }
}
