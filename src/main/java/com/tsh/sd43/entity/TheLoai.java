package com.tsh.sd43.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TheLoai")
public class TheLoai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "IdThuongHieu")
    private Long idThuongHieu;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "NgayTao")
    private String ngayTao;

    @Column(name = "NgayCapNhat")
    private String ngayCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
