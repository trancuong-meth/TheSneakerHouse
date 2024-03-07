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

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "gia_tri_giam")
    private BigDecimal giaTriGiam;

    @Column(name = "trong_luong")
    private Float trongLuong;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham")
    private SanPham idSanPham;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mau_sac")
    private MauSac idMauSac;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_kich_co")
    private KichCo idKichCo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dot_giam_gia")
    private DotGiamGia idDotGiamGia;

}
