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
@Table(name = "IDotGiamGiaRepo")
public class DotGiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "PhanTramGiam")
    private Float phanTramGiam;

    @Column(name = "NgayBatDau")
    private Timestamp ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Timestamp ngayKetThuc;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
