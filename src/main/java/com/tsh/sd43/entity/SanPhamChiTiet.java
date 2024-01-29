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
@Table(name = "SanPham")
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "IdSanPham")
    private Long idThuongHieu;

    @Column(name = "IdTheLoai")
    private Long idTheLoai;

    @Column(name = "IdMauSac")
    private Long idMauSac;

    @Column(name = "IdKichCo")
    private Long idKichCo;

    @Column(name = "QRcode")
    private String QRcode;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @Column(name = "TrongLuong")
    private Float trongLuong;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
