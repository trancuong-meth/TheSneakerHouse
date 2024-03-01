package com.tsh.sd43.entity.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CustomerAddResquest {

    private String ten;

    private Date ngaySinh;

    private Boolean gioiTinh;

    private String soDienThoai;

    private String cccd;

    private String email;

    private String avatar;

    private String xa;

    private String phuong;

    private String tinh;

    private String diaChi;

    private String maXa;

    private String maPhuong;

    private String maTinh;
}
