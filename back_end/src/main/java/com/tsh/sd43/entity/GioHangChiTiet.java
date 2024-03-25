package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gio_hang")
    private GioHang idGioHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTiet idSanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

}
