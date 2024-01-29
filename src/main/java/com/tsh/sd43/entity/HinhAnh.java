package com.tsh.sd43.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "HinhAnh")
public class HinhAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdSanPhamChiTiet")
    private Long idSanPhamChiTiet;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "TenFile")
    private String tenFile;

    @Column(name = "DuLieuHinhAnh")
    private String duLieuHinhAnh;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
