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
@Table(name = "GioHang")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdKhachHang")
    private Long idKhachHang;

    @Column(name = "TongSoLuong")
    private Integer tongSoLuong;

    @Column(name = "TongTien")
    private BigDecimal tongTien;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
