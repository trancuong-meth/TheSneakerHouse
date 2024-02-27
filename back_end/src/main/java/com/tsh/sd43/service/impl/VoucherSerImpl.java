package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.repository.IVoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VoucherSerImpl {

    @Autowired
    private IVoucherRepo voucherRepo;

    public Page<Voucher> getVouchers(Integer pageNo){
        Pageable pageable = PageRequest.of(pageNo, 3);
        return voucherRepo.findVouchersByState(pageable);
    }

    public Voucher getVoucherById(Long id){
        return voucherRepo.findVoucherByIdAndState(id).get(0);
    }

    public Voucher addVoucher(VoucherAddRequest req){
        // add voucher
        Voucher voucher = new Voucher();

        voucher.setMa(generateCode());
        voucher.setTen(req.getTen());
        voucher.setPhanTramGiam(req.getPhanTramGiam());
        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
        voucher.setSoLanDung(req.getSoLanDung());
        voucher.setNgayBatDau(req.getNgayBatDau());
        voucher.setNgayKetThuc(req.getNgayKetThuc());
        voucher.setTrangThai(req.getTrangThai());

        return voucherRepo.save(voucher);
    }

    public Voucher updateVoucher(VoucherAddRequest req){
        // add voucher
        Voucher voucher = new Voucher();

        voucher.setId(req.getId());
        voucher.setMa(req.getMa());
        voucher.setTen(req.getTen());
        voucher.setPhanTramGiam(req.getPhanTramGiam());
        voucher.setGiaTriToiThieu(req.getGiaTriToiThieu());
        voucher.setGiaTriToiDa(req.getGiaTriToiDa());
        voucher.setSoLanDung(req.getSoLanDung());
        voucher.setNgayBatDau(req.getNgayBatDau());
        voucher.setNgayKetThuc(req.getNgayKetThuc());
        voucher.setTrangThai(req.getTrangThai());

        return voucherRepo.save(voucher);
    }

    public void changeState(Long id){
        Voucher voucher = voucherRepo.findById(id).isPresent() ? voucherRepo.findById(id).get() : null;

        if(voucher == null){
            return;
        }
        voucher.setTrangThai(1);
        voucherRepo.save(voucher);

    }

    public String generateCode(){
        // generate code
        String newestCode = voucherRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "VOUCHER_" + 0;
        }
        return "VOUCHER_" + Integer.parseInt(newestCode.substring(8)) + 1;
    }

}
