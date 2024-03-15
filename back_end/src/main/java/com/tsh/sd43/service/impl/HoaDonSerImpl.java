package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.ProductVoucherUpdateRequest;
import com.tsh.sd43.entity.responce.BillStateResponce;
import com.tsh.sd43.entity.responce.ProductBestSellerResponce;
import com.tsh.sd43.entity.responce.RevenueRangeDateResponce;
import com.tsh.sd43.entity.responce.RevenueResponce;
import com.tsh.sd43.enums.StatusHoaDon;
import com.tsh.sd43.repository.IHoaDonRepo;
import com.tsh.sd43.service.IHoaDonChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class HoaDonSerImpl implements IHoaDonChiTietSer {

    @Autowired
    private IHoaDonRepo hoaDonRepo;

    public ArrayList<HoaDon> getHoaDonByTrangThai(Integer trangThai) {
        return (ArrayList<HoaDon>) hoaDonRepo.getHoaDonByTrangThai(trangThai);
    }

    public HoaDon createBillWait() {

        ArrayList<HoaDon> orders = getHoaDonByTrangThai(0);
        if(orders.size() == 6){
            throw new RuntimeException("Bạn chỉ có thể tạo tối đa 6 đơn hàng.");
        }

        HoaDon hoaDon = new HoaDon();

        hoaDon.setMa(generateCode());
        hoaDon.setTrangThai(StatusHoaDon.CHO.getTrangThai());
        return hoaDonRepo.save(hoaDon);
    }

    public String generateCode() {
        // generate code
        String newestCode = hoaDonRepo.generateNewestCode();
        if (newestCode == null || newestCode.equals("")) {
            return "HD" + 0;
        }
        return "HD" + (Integer.parseInt(newestCode.substring(2)) + 1);
    }

    public String deleteBillById(Long id){
        hoaDonRepo.deleteBillByIdBill(id);
        return "success";
    }

    public HoaDon addVoucherToBill(ProductVoucherUpdateRequest req) {
        HoaDon hoaDon = req.getHoaDon();
        hoaDon.setIdVoucher(req.getVoucher());
        return hoaDonRepo.save(hoaDon);
    }

    public HoaDon updateHoaDon(HoaDon hoaDon) {
        return hoaDonRepo.save(hoaDon);
    }

    public Voucher getVoucherByIdBill(Long id) {
        ArrayList<HoaDon> hoaDons = hoaDonRepo.getHoaDonByTrangThai(0);
        for(HoaDon hoaDon : hoaDons){
            if(hoaDon.getId().equals(id)){
                return hoaDon.getIdVoucher();
            }
        }
        return null;
    }

    public KhachHang getCustomerByIdBill(Long id) {
        ArrayList<HoaDon> hoaDons = hoaDonRepo.getHoaDonByTrangThai(0);
        for(HoaDon hoaDon : hoaDons){
            if(hoaDon.getId().equals(id)){
                return hoaDon.getIdKhachHang();
            }
        }
        return null;
    }

    public Page<HoaDon> getBillAndPanigation(Integer pageNo, Integer pageSize, Integer state) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if(state == -1){
            return hoaDonRepo.getAllBillPanigation(pageable);
        }
        return hoaDonRepo.getBillPanigationByState(pageable, state);
    }

    public HoaDon getBillById(Long id){
        HoaDon hoaDon =  hoaDonRepo.findById(id).get();
        if(hoaDon == null){
            throw new RuntimeException("Không tìm tấy hóa đơn này.");
        }
        return hoaDon;
    }

    public RevenueResponce getRevenueMonth() {
        return hoaDonRepo.getRevenueMonth();
    }

    public RevenueResponce getRevenueDay() {
        return hoaDonRepo.getRevenueDay();
    }

    public Integer getQuantityOfProductWithMonth() {
        return hoaDonRepo.getQuantityOfProductWithMonth();
    }

    public ArrayList<RevenueRangeDateResponce> getRevenueRangeDate(Date startDate, Date endDate) {
        return hoaDonRepo.getRevenueRangeDate(startDate, endDate);
    }

    public ArrayList<ProductBestSellerResponce> getBestSeller() {
        return hoaDonRepo.getTop5ProductBestSeller();
    }

    public ArrayList<BillStateResponce> getBillState() {
        return hoaDonRepo.getBillState();
    }
}
