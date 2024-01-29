package com.tsh.sd43.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "GioHangChiTiet")
public class GioHangChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdGioHang")
    private Long idGioHang;

    @Column(name = "IdSanPhamChiTiet")
    private Long idSanPhamChiTiet;

    @Column(name = "SoLuong")
    private Integer soLuong;
}
