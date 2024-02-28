package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.DotGiamGia;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.SaleAddRequest;
import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.enums.StatusVoucher;
import com.tsh.sd43.repository.IDotGiamGiaRepo;
import com.tsh.sd43.service.IDotGiamGiaSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class DotGiamGiaSerImpl implements IDotGiamGiaSer {

    @Autowired
    private IDotGiamGiaRepo dotGiamGiaRepo;


    public Page<Voucher> getSales(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return dotGiamGiaRepo.findSalesByState(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<DotGiamGia> getAllSales(){
//        get all voucher
        return (ArrayList<DotGiamGia>) dotGiamGiaRepo.findAll();
    }

    public DotGiamGia getSaleById(Long id){
        return dotGiamGiaRepo.findSalesByIdAndState(id).get(0);
    }

    public DotGiamGia addSale(SaleAddRequest req){
        // check state
        Date today = new Date();

        // add sale
        DotGiamGia sale = new DotGiamGia();
        sale.setMa(generateCode());
        sale.setTen(req.getTen());
        sale.setPhanTramGiam(req.getPhanTramGiam());
        sale.setNgayBatDau(req.getNgayBatDau());
        sale.setNgayKetThuc(req.getNgayKetThuc());

        if(req.getNgayBatDau().after(today)){
            sale.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
        }
        if(req.getNgayKetThuc().before(today)){
            sale.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
        }
        if(req.getNgayBatDau().before(today) && req.getNgayKetThuc().after(today)){
            sale.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
        }

        return dotGiamGiaRepo.save(sale);
    }

    public String changeState(Long id){
        // check state
        Date today = new Date();
        DotGiamGia sale = dotGiamGiaRepo.findById(id).isPresent() ? dotGiamGiaRepo.findById(id).get() : null;

        if(sale == null){
            return "Không tìm thấy đợt giảm giá";
        }

        if(sale.getTrangThai() == StatusVoucher.KET_THUC.getTrangThai()){
            return "Voucher đã kết thúc";
        }

        if(sale.getTrangThai() == StatusVoucher.HUY.getTrangThai()){
            if(sale.getNgayBatDau().after(today)){
                sale.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
            }
            if(sale.getNgayKetThuc().before(today)){
                sale.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
            }
            if(sale.getNgayBatDau().before(today) && sale.getNgayKetThuc().after(today)){
                sale.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
            }
            dotGiamGiaRepo.save(sale);
            return "Đợt giảm giá khôi phục thành công";
        }

        sale.setTrangThai(StatusVoucher.HUY.getTrangThai());
        dotGiamGiaRepo.save(sale);
        return "Thay đổi trạng thái thành công";
    }

    public String generateCode(){
        // generate code
        String newestCode = dotGiamGiaRepo.generateNewestCode();
        if(newestCode == null || newestCode.equals("")){
            return "SALE_" + 0;
        }
        return "SALE_" + (Integer.parseInt(newestCode.substring(5)) + 1);
    }

    public DotGiamGia editVoucher(DotGiamGia sale){
        Date today = new Date();

        if(sale.getNgayBatDau().after(today)){
            sale.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
        }
        if(sale.getNgayKetThuc().before(today)){
            sale.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
        }
        if(sale.getNgayBatDau().before(today) && sale.getNgayKetThuc().after(today)){
            sale.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
        }
        return dotGiamGiaRepo.save(sale);
    }


}
