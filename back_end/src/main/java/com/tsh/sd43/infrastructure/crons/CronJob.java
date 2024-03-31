package com.tsh.sd43.infrastructure.crons;

import com.tsh.sd43.entity.DotGiamGia;
import com.tsh.sd43.entity.PhieuGiamGiaChiTiet;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.enums.StatusDotGiamGia;
import com.tsh.sd43.enums.StatusVoucher;
import com.tsh.sd43.repository.IDotGiamGiaRepo;
import com.tsh.sd43.repository.ISanPhamChiTietRepo;
import com.tsh.sd43.repository.IVoucherRepo;
import com.tsh.sd43.repository.PhieuGiamGiaChiTietRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CronJob {

    private static final Logger logger = LoggerFactory.getLogger(CronJob.class);

    @Autowired
    private IVoucherRepo voucherRepo;

    @Autowired
    private IDotGiamGiaRepo dotGiamGiaRepo;

    @Autowired
    private ISanPhamChiTietRepo sanPhamChiTietRepo;

    @Autowired
    private PhieuGiamGiaChiTietRepo phieuGiamGiaChiTietRepo;

    @Scheduled(fixedRate = 1000)
    public void cronJobVoucher(){
        Date today = new Date();
        List<Voucher> vouchers = voucherRepo.findAll();
        vouchers.forEach(voucher -> {
            if(voucher.getNgayBatDau().after(today)){
                voucher.setTrangThai(StatusVoucher.CHUA_BAT_DAU.getTrangThai());
            }else if(voucher.getNgayKetThuc().before(today)){
                voucher.setTrangThai(StatusVoucher.KET_THUC.getTrangThai());
                // khi phieu giam gia ket thuc thi lap tuc nhung kh co voucher nay se mat ap dung

                List<PhieuGiamGiaChiTiet> voucherDetails = phieuGiamGiaChiTietRepo.getChiTietByPhieuGiamGia(voucher.getId());
                voucherDetails.forEach(voucherDetail -> {
                    phieuGiamGiaChiTietRepo.deleteById(voucherDetail.getId());
                });
            }else{
                voucher.setTrangThai(StatusVoucher.DANG_DIEN_RA.getTrangThai());
            }
            voucherRepo.save(voucher);

        });
    }

    @Scheduled(fixedRate = 1000)
    public void cronJobSale(){
        Date today = new Date();
        List<DotGiamGia> sales = dotGiamGiaRepo.findAll();
        sales.forEach(sale -> {
            if(sale.getNgayBatDau().after(today)){
                sale.setTrangThai(StatusDotGiamGia.CHUA_BAT_DAU.getTrangThai());
            }else if(sale.getNgayKetThuc().before(today)){
                sale.setTrangThai(StatusDotGiamGia.KET_THUC.getTrangThai());
                // Kh dot giam gia ket thuc lap tuc xoa tat ca cac sanpham co ap dung
                List<SanPhamChiTiet> productDetails = sanPhamChiTietRepo.getProductDetailsByIdSale(sale.getId());
                productDetails.forEach(productDetail -> {
                    productDetail.setIdDotGiamGia(null);
                    sanPhamChiTietRepo.save(productDetail);
                });
            }else{
                sale.setTrangThai(StatusDotGiamGia.DANG_DIEN_RA.getTrangThai());
            }
            dotGiamGiaRepo.save(sale);
        });
    }

}
