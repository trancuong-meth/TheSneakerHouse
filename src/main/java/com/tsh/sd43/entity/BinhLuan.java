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
@Table(name = "BinhLuan")
public class BinhLuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdKhachHang")
    private Long idKhachHang;

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "NgayTao")
    private String ngayTao;

    @Column(name = "NgayCapNhat")
    private String ngayCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
