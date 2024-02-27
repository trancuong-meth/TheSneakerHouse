package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "hinh_anh")
public class HinhAnh extends PrimaryEntity {

    @Column(name = "id_san_pham_chi_tiet")
    private Long idSanPhamChiTiet;

    @Column(name = "duong_dan")
    private String duongDan;

    @Column(name = "trang_thai")
    private Boolean trangThai;
}
