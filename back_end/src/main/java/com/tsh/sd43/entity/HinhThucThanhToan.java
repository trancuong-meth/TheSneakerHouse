package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hinh_thuc_thanh_toan")
public class HinhThucThanhToan extends PrimaryEntity {
    
    @Column(name = "loai_thanh_toan")
    private Integer loaiThanhToan;

    @Column(name = "so_tien_thanh_toan")
    private BigDecimal soTienThanhToan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Boolean trangThai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon idHoaDon;
}
