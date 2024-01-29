package com.tsh.sd43.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "MauSac")
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "NgayTao")
    private Timestamp ngayTao;

    @Column(name = "NgayCapNhat")
    private Timestamp ngayCapNhat;

    @Column(name = "TrangThai")
    private Boolean trangThai;
}
