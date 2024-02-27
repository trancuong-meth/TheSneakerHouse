package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon idHoaDon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTiet idSanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "tong_tien")
    private BigDecimal tongTien;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "don_gia_sau_khi_giam")
    private BigDecimal donGiaSauKhiGiam;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
