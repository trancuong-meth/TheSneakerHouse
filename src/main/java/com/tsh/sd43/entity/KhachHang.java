package com.tsh.sd43.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "KhachHang")
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "TenDangNhap")
    private String tenDangNhap;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "Email")
    private String email;

    @Column(name = "LoaiKhach")
    private Boolean loaiKhach;

    @Column(name = "TenFileHinhAnh")
    private String tenFile;

    @Column(name = "DuLieuHinhAnh")
    private byte[] duLieuHinhAnh;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
