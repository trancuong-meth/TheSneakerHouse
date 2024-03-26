package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phieu_giam_gia_chi_tiet")
public class PhieuGiamGiaChiTiet extends PrimaryEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_phieu_giam_gia")
    private Voucher idPhieuGiamGia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang idKhachHang;

}
