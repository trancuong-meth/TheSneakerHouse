package com.tsh.sd43.entity.responce;

import com.tsh.sd43.entity.HoaDon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {HoaDon.class})
public interface RevenueFillterResponce {

    @Value("#{target.so_luong}")
    Integer getSoLuong();

    @Value("#{target.tong_tien}")
    BigDecimal getTongTien();

    @Value("#{target.ngay}")
    String getNgay();

}
