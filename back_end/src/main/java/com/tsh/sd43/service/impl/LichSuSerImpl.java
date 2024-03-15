package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.LichSu;
import com.tsh.sd43.entity.request.HistoryRequest;
import com.tsh.sd43.repository.iLichSuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LichSuSerImpl {

    @Autowired
    private iLichSuRepo lichSuRepo;

    public ArrayList<LichSu> getAllLichSu(Long id){
        return lichSuRepo.findAllByIdHoaDon(id);
    }

    public LichSu addHistory(HistoryRequest lichSu){
        LichSu history = new LichSu();

//        update history
        history.setTrangThai(lichSu.getTrangThai());
        history.setGhiChu(lichSu.getGhiChu());
        history.setIdHoaDon(lichSu.getHoaDon());
        return lichSuRepo.save(history);
    }

}
