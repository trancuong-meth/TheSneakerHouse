package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.HoaDonChiTiet;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.request.ProductDetailRequest;
import com.tsh.sd43.repository.IHoaDonChiTietRepo;
import com.tsh.sd43.service.IHoaDonChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HoaDonChiTietSerImpl implements IHoaDonChiTietSer {

    @Autowired
    private IHoaDonChiTietRepo hoaDonChiTietRepo;

    public ArrayList<HoaDonChiTiet> findProductDetailsByIdProduct(Long id) {
        try {
            return hoaDonChiTietRepo.findBillDetailByIdBill(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HoaDonChiTiet addProductToBill(ProductDetailRequest req) {
        try {
            ArrayList<HoaDonChiTiet> productDetails = hoaDonChiTietRepo.findBillDetailByIdBill(req.getHoaDon().getId());

            if(productDetails.size() == 0) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                hoaDonChiTiet.setSoLuong(1);
                hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                hoaDonChiTiet.setTrangThai(1);
                return hoaDonChiTietRepo.save(hoaDonChiTiet);
            }else{
                for(HoaDonChiTiet item : productDetails) {
                    if(item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {

                        if(req.getSoLuong() == -1) {
                            if(item.getSoLuong() + 1 > req.getSanPhamChiTiet().getSoLuongTon()) {
                                throw new RuntimeException("Số lượng tồn không đủ");
                            }

                            item.setSoLuong(item.getSoLuong() + 1);
                            return hoaDonChiTietRepo.save(item);
                        }else{
                            if(req.getSanPhamChiTiet().getSoLuongTon() >= req.getSoLuong()) {
                                item.setSoLuong(req.getSoLuong());
                                return hoaDonChiTietRepo.save(item);
                            }else{
                                throw new RuntimeException("Số lượng tồn không đủ");
                            }
                        }


                    }
                }

                if(req.getSoLuong() == -1) {
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                    hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    hoaDonChiTiet.setSoLuong(1);
                    hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                    hoaDonChiTiet.setTrangThai(1);
                    return hoaDonChiTietRepo.save(hoaDonChiTiet);
                }else{
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                    hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    hoaDonChiTiet.setSoLuong(req.getSoLuong());
                    hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                    hoaDonChiTiet.setTrangThai(1);
                    return hoaDonChiTietRepo.save(hoaDonChiTiet);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBillDetailById(Long id) {
        try {
            hoaDonChiTietRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
