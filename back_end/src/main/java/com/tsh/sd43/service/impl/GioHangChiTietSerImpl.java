package com.tsh.sd43.service.impl;

import com.tsh.sd43.entity.GioHang;
import com.tsh.sd43.entity.GioHangChiTiet;
import com.tsh.sd43.repository.IGioHangChiTietRepo;
import com.tsh.sd43.repository.IGioHangRepo;
import com.tsh.sd43.service.IGioHangChiTietSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GioHangChiTietSerImpl implements IGioHangChiTietSer {

    @Autowired
    private IGioHangChiTietRepo gioHangChiTietRepo;

    public void removeCartDetailById(Long id){
        gioHangChiTietRepo.deleteById(id);
    }

}
