package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "san_pham")
public class SanPham extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_thuong_hieu")
    private ThuongHieu idThuongHieu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_dot_giam_gia")
    private DotGiamGia idDotGiamGia;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "gia_tri_giam")
    private BigDecimal giaTriGiam;

    @Column(name = "trang_thai")
    private Boolean trangThai;
}
