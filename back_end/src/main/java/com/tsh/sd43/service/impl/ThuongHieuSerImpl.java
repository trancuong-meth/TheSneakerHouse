package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.ThuongHieu;
import com.tsh.sd43.entity.Voucher;
import com.tsh.sd43.entity.request.VoucherAddRequest;
import com.tsh.sd43.enums.StatusVoucher;
import com.tsh.sd43.repository.IThuongHieuRepo;
import com.tsh.sd43.service.IThuongHieuSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ThuongHieuSerImpl implements IThuongHieuSer {

    @Autowired
    private IThuongHieuRepo thuongHieuRepo;


    public Page<ThuongHieu> getBrands(int pageNo, int pageSize, String key, String trangThai){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return thuongHieuRepo.findBrandsByStateAndName(pageable,
                "%" + trangThai + "%",
                "%" + key + "%");
    }

    public ArrayList<ThuongHieu> getAllBrands(){
//        get all voucher
        return (ArrayList<ThuongHieu>) thuongHieuRepo.findAll();
    }

    public ThuongHieu getBrandById(Long id){
        return thuongHieuRepo.findBrandsById(id).get(0);
    }

    public ThuongHieu addBrand(String req){
      ThuongHieu brand = new ThuongHieu();
      brand.setTen(req);

      return thuongHieuRepo.save(brand);
    }

    public ThuongHieu updateBrand(ThuongHieu req){
        return thuongHieuRepo.save(req);
    }

}
