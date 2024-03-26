package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.PhieuGiamGiaChiTiet;
import com.tsh.sd43.entity.request.VoucherDetailRequest;
import com.tsh.sd43.repository.PhieuGiamGiaChiTietRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PhieuGiamGiaChiTietSerImpl {

    @Autowired
    private PhieuGiamGiaChiTietRepo phieuGiamGiaChiTietRepo;

    public PhieuGiamGiaChiTiet addVoucherDetail(VoucherDetailRequest voucherDetailRequest){
        PhieuGiamGiaChiTiet voucherDetail = new PhieuGiamGiaChiTiet();
        voucherDetail.setIdPhieuGiamGia(voucherDetailRequest.getVoucher());
        voucherDetail.setIdKhachHang(voucherDetailRequest.getKhachHang());
        return phieuGiamGiaChiTietRepo.save(voucherDetail);
    }

    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetail(Long idPhieuGiamGia){
        return (ArrayList<PhieuGiamGiaChiTiet>) phieuGiamGiaChiTietRepo.getChiTietByPhieuGiamGia(idPhieuGiamGia);
    }

    public ArrayList<PhieuGiamGiaChiTiet> getAllVoucherDetailByIdCustomer(Long idKhachHang){
        return phieuGiamGiaChiTietRepo.getChiTietByPhieuGiamGiaByIdKhachHang(idKhachHang);
    }

    public void deleteVoucherDetail(Long id){
        phieuGiamGiaChiTietRepo.deleteById(id);
    }
}
