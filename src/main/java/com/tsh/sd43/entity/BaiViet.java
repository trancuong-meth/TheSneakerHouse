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
@Table(name = "BaiViet")
public class BaiViet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdNhanVien")
    private Long idNhanVien;

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "TieuDe")
    private String tieuDe;

    @Column(name = "NgayTao")
    private String ngayTao;

    @Column(name = "NgayCapNhat")
    private String ngayCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
