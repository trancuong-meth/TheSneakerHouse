package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.HinhThucThanhToan;
import com.tsh.sd43.entity.request.PaymentMethodAddRequest;
import com.tsh.sd43.repository.IHinhThucThanhToanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HinhThucThanhToanSerImpl {

    @Autowired
    private IHinhThucThanhToanRepo hinhThucThanhToanRepo;

    public HinhThucThanhToan addPaymentMethod(PaymentMethodAddRequest req){
        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();

        hinhThucThanhToan.setLoaiThanhToan(req.getLoaiThanhToan());
        hinhThucThanhToan.setSoTienThanhToan(req.getSoTienThanhToan());
        hinhThucThanhToan.setGhiChu(req.getGhiChu());
        hinhThucThanhToan.setIdHoaDon(req.getIdHoaDon());
        hinhThucThanhToan.setTrangThai(req.getDeleted());
        return hinhThucThanhToanRepo.save(hinhThucThanhToan);
    }

    public ArrayList<HinhThucThanhToan> getAllPaymentMethodByIdBill(Long id){
        return hinhThucThanhToanRepo.getAllPaymentMethodByIdBill(id);
    }

}
