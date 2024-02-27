package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet extends PrimaryEntity {

    @Column(name = "id_gio_hang")
    private Long idGioHang;

    @Column(name = "id_san_pham_chi_tiet")
    private Long idSanPhamChiTiet;

    @Column(name = "so_luong")
    private Integer soLuong;

}
