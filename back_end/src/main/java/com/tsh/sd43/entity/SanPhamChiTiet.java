package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet extends PrimaryEntity {

    @Column(name = "QRcode")
    private String QRcode;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "trong_luong")
    private Float trongLuong;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_the_loai")
    private TheLoai idTheLoai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mau_sac")
    private MauSac idMauSac;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kich_co")
    private KichCo idKichCo;

}
