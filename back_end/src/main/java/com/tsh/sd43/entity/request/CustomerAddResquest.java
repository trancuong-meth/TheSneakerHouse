package com.tsh.sd43.entity.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CustomerAddResquest {

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "xa")
    private String xa;

    @Column(name = "phuong")
    private String phuong;

    @Column(name = "tinh")
    private String tinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ma_xa")
    private String maXa;

    @Column(name = "ma_phuong")
    private String maPhuong;

    @Column(name = "ma_tinh")
    private String maTinh;
}
