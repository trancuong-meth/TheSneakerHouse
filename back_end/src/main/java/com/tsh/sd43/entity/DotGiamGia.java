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
@Table(name = "dot_giam_gia")
public class DotGiamGia extends PrimaryEntity {

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "phan_tram_giam")
    private Float phanTramGiam;

    @Column(name = "ngay_bat_dau")
    private Timestamp ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private Timestamp ngayKetThuc;

    @Column(name = "trang_thai")
    private Boolean trangThai;
}
