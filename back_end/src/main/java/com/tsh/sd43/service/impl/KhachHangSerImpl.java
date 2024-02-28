package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.enums.StatusVoucher;
import com.tsh.sd43.repository.IKhachHangRepo;
import com.tsh.sd43.repository.IVoucherRepo;
import com.tsh.sd43.service.IKhachHangSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class KhachHangSerImpl implements IKhachHangSer {

    @Autowired
    private IKhachHangRepo khachHangRepo;

    public Page<KhachHang> getCustomersWithPanigation(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return khachHangRepo.findCustomers(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<KhachHang> getAllCustomers(){
//        get all voucher
        return (ArrayList<KhachHang>) khachHangRepo.findAll();
    }

    public KhachHang getVoucherById(Long id){
        return khachHangRepo.findKhachHangById(id).get(0);
    }

//    public Voucher addVoucher(VoucherAddRequest req){
//        // check state
//        Date today = new Date();
//
//        // add voucher
//        Voucher voucher = new Voucher();
//
//        voucher.setMa(generateCode());
//        voucher.setTen(req.getTen());
//        voucher.setPhanTramGiam(req.getPhanTramGiam());
//        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
//        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
//        voucher.setSoLanDung(req.getSoLanDung());
//        voucher.setNgayBatDau(req.getNgayBatDau());
//        voucher.setNgayKetThuc(req.getNgayKetThuc());
//
//        if(req.getNgayBatDau().after(today)){
//            voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
//        }
//        if(req.getNgayKetThuc().before(today)){
//            voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
//        }
//        if(req.getNgayBatDau().before(today) && req.getNgayKetThuc().after(today)){
//            voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
//        }
//
//        return voucherRepo.save(voucher);
//    }
//
//    public Voucher updateVoucher(VoucherAddRequest req){
//        // add voucher
//        Voucher voucher = new Voucher();
//
//        voucher.setId(req.getId());
//        voucher.setMa(req.getMa());
//        voucher.setTen(req.getTen());
//        voucher.setPhanTramGiam(req.getPhanTramGiam());
//        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
//        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
//        voucher.setSoLanDung(req.getSoLanDung());
//        voucher.setNgayBatDau(req.getNgayBatDau());
//        voucher.setNgayKetThuc(req.getNgayKetThuc());
//        voucher.setTrangThai(req.getTrangThai());
//
//        return voucherRepo.save(voucher);
//    }

    public String generateCode(){
        // generate code
        String newestCode = khachHangRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "CUSTOMER_" + 0;
        }
        return "CUSTOMER_" + (Integer.parseInt(newestCode.substring(9)) + 1);
    }

//    public Voucher editVoucher(Voucher voucher){
//        Date today = new Date();
//
//        if(voucher.getNgayBatDau().after(today)){
//            voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
//        }
//        if(voucher.getNgayKetThuc().before(today)){
//            voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
//        }
//        if(voucher.getNgayBatDau().before(today) && voucher.getNgayKetThuc().after(today)){
//            voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
//        }
//        return voucherRepo.save(voucher);
//    }
}
