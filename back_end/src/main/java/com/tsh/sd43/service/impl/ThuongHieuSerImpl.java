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
import java.util.List;

@Service
public class ThuongHieuSerImpl implements IThuongHieuSer {

    @Autowired
    private IThuongHieuRepo thuongHieuRepo;


    public Page<ThuongHieu> getBrands(int pageNo, int pageSize, String key){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return thuongHieuRepo.findBrandsByStateAndName(pageable,
                "%" + key + "%");
    }

    public ArrayList<ThuongHieu> getAllBrands(){
//        get all voucher
        return (ArrayList<ThuongHieu>) thuongHieuRepo.findByOrderByNgayTaoDesc();
    }

    public ThuongHieu getBrandById(Long id){
        return thuongHieuRepo.findBrandsById(id).get(0);
    }

    public ThuongHieu addBrand(String req){
      ThuongHieu brand = new ThuongHieu();

      if(!thuongHieuRepo.findBrandsByName(req).isEmpty()){
          throw new RuntimeException("The brand is already exists");
      }
      brand.setTen(req);

      return thuongHieuRepo.save(brand);
    }

    public ThuongHieu updateBrand(ThuongHieu req){
        List<ThuongHieu> brands = thuongHieuRepo.findBrandsByName(req.getTen());

        if(!brands.isEmpty()){
            if(!brands.get(0).getId().equals(req.getId())){
                if(brands.get(0).getTen().equals(req.getTen())){
                    throw new RuntimeException("The brand is already exists");
                }
            }
        }

        return thuongHieuRepo.save(req);
    }

}
