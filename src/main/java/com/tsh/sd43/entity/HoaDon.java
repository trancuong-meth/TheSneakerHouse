package com.tsh.sd43.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "HoaDon")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdNhanVien")
    private Long idNhanVien;

    @Column(name = "IdKhachHang")
    private Long idKhachHang;

    @Column(name = "IdVoucher")
    private Long idVoucher;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NgayXacNhan")
    private Timestamp ngayXacNhan;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "SdtNguoiNhan")
    private String sdtNguoiNhan;

    @Column(name = "TenShip")
    private String tenNguoiShip;

    @Column(name = "SdtShip")
    private String sdtNguoiShip;

    @Column(name = "NgayVanChuyen")
    private Timestamp ngayVanChuyen;

    @Column(name = "NgayHoanThanh")
    private Timestamp ngayHoanThanh;

    @Column(name = "PhiVanChuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "GiaTriGiam")
    private BigDecimal giaTriGiam;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "PhuongThucThanhToan")
    private Integer phuongThucThanhToan;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
