package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.SanPhamChiTiet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailRequest {

    SanPhamChiTiet sanPhamChiTiet;

    Integer soLuong;

    Long idKhachHang;

}
