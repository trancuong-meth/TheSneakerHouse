package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.KichCo;
import com.tsh.sd43.entity.ThuongHieu;
import com.tsh.sd43.repository.IKichCoRepo;
import com.tsh.sd43.repository.IThuongHieuRepo;
import com.tsh.sd43.service.IKichCoSer;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KichCoSerImpl implements IKichCoSer {

    @Autowired
    private IKichCoRepo kichCoRepo;


    public Page<KichCo> getSizes(int pageNo, int pageSize, String key){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return kichCoRepo.findSizePanigation(pageable,
                "%" + key + "%");
    }

    public ArrayList<KichCo> getAllSize(){
//        get all voucher
        return (ArrayList<KichCo>) kichCoRepo.findAll();
    }

    public KichCo getSizeById(Long id){
        return kichCoRepo.findSizeById(id).get(0);
    }

    public KichCo addSize(Integer req){
        KichCo size = new KichCo();

        if(!kichCoRepo.findSizeBySize(req).isEmpty()){
            throw new RuntimeException("The size is already exists");
        }
        size.setKichCo(req);

        return kichCoRepo.save(size);
    }

    public KichCo updateSize(KichCo req){
        ArrayList<KichCo> sizes = kichCoRepo.findSizeBySize(req.getKichCo());

        if(!sizes.isEmpty()){
            if(!sizes.get(0).getId().equals(req.getId())){
                if(sizes.get(0).getKichCo().equals(req.getKichCo())){
                    throw new RuntimeException("The size is already exists");
                }
            }
        }

        return kichCoRepo.save(req);
    }
}
