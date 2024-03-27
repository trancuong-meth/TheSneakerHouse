package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.HoaDonChiTiet;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.request.ProductDetailRequest;
import com.tsh.sd43.repository.IHoaDonChiTietRepo;
import com.tsh.sd43.repository.ISanPhamChiTietRepo;
import com.tsh.sd43.service.IHoaDonChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class HoaDonChiTietSerImpl implements IHoaDonChiTietSer {

    @Autowired
    private IHoaDonChiTietRepo hoaDonChiTietRepo;

    public Page<HoaDonChiTiet> findProductDetailsByIdProduct(Integer pageNo, Integer pageSize, Long id) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            return hoaDonChiTietRepo.findBillDetailByIdBill(pageable, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HoaDonChiTiet addProductToBill(ProductDetailRequest req) {
        try {
            ArrayList<HoaDonChiTiet> productDetails = hoaDonChiTietRepo.findBillDetailsByIdBill(req.getHoaDon().getId());

            if(productDetails.size() == 0) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                hoaDonChiTiet.setSoLuong(1);
                hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                hoaDonChiTiet.setTrangThai(1);
                if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                    hoaDonChiTiet.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 - req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                            * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                }
                return hoaDonChiTietRepo.save(hoaDonChiTiet);
            }else{
                for(HoaDonChiTiet item : productDetails) {
                    if(item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {

                        if(req.getSoLuong() == -1) {
                            if(item.getSoLuong() + 1 > req.getSanPhamChiTiet().getSoLuongTon()) {
                                throw new RuntimeException("Số lượng còn lại không đủ");
                            }
                            if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                                item.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                                        * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                            }
                            item.setSoLuong(item.getSoLuong() + 1);
                            return hoaDonChiTietRepo.save(item);
                        }else{
                            if(req.getSanPhamChiTiet().getSoLuongTon() >= req.getSoLuong()) {
                                item.setSoLuong(req.getSoLuong());
                                if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                                    item.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 - req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                                            * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                                }
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
                    if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                        hoaDonChiTiet.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                                * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                    }
                    return hoaDonChiTietRepo.save(hoaDonChiTiet);
                }else{
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                    hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                    hoaDonChiTiet.setSoLuong(req.getSoLuong());
                    hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                    hoaDonChiTiet.setTrangThai(1);
                    if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                        hoaDonChiTiet.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                                * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                    }
                    return hoaDonChiTietRepo.save(hoaDonChiTiet);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HoaDonChiTiet addProductToBillClient(ProductDetailRequest req) {
        try {

            if(req.getSoLuong() > req.getSanPhamChiTiet().getSoLuongTon()) {
                throw new RuntimeException("Số lượng còn lại không đủ");
            }
            ArrayList<HoaDonChiTiet> billDetails = hoaDonChiTietRepo.findBillDetailsByIdBill(req.getHoaDon().getId());

            if(billDetails.size() == 0) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                hoaDonChiTiet.setSoLuong(req.getSoLuong());
                hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                hoaDonChiTiet.setTrangThai(1);
                if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                    hoaDonChiTiet.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                            * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                }
                return hoaDonChiTietRepo.save(hoaDonChiTiet);
            }else {
                for(HoaDonChiTiet item : billDetails) {
                    if(item.getIdSanPhamChiTiet().getId().equals(req.getSanPhamChiTiet().getId())) {
                        item.setSoLuong(req.getSoLuong());
                        if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                            item.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                                    * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                        }

                        return hoaDonChiTietRepo.save(item);
                    }
                }

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdHoaDon(req.getHoaDon());
                hoaDonChiTiet.setIdSanPhamChiTiet(req.getSanPhamChiTiet());
                hoaDonChiTiet.setSoLuong(req.getSoLuong());
                hoaDonChiTiet.setDonGia(req.getSanPhamChiTiet().getDonGia());
                hoaDonChiTiet.setTrangThai(1);
                if(req.getSanPhamChiTiet().getIdDotGiamGia() != null) {
                    hoaDonChiTiet.setDonGiaSauKhiGiam(BigDecimal.valueOf((100 -req.getSanPhamChiTiet().getIdDotGiamGia().getPhanTramGiam())
                            * req.getSanPhamChiTiet().getDonGia().doubleValue()/100));
                }
                return hoaDonChiTietRepo.save(hoaDonChiTiet);

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

    public HoaDonChiTiet updateBillDetail(HoaDonChiTiet req) {
        return hoaDonChiTietRepo.save(req);
    }
}
