package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "nhan_vien")
public class NhanVien extends PrimaryEntity {

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chuc_vu")
    private ChucVu chucVu;

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
