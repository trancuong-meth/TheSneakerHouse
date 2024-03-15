package com.tsh.sd43.entity.responce;

import com.tsh.sd43.entity.HoaDon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {HoaDon.class})
public interface BillStateResponce {

    @Value("#{target.trang_thai}")
    Integer getTrangThai();

    @Value("#{target.so_luong}")
    Integer getSoLuong();
}
