package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "khach_hang")
public class KhachHang extends PrimaryEntity {

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ten_dang_nhap")
    private String tenDangNhap;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "loai_khach")
    private Boolean loaiKhach;

    @Column(name = "ten_file_hinh_anh")
    private String tenFile;

    @Column(name = "du_lieu_hinh_anh")
    private byte[] duLieuHinhAnh;

    @Column(name = "xa")
    private String xa;

    @Column(name = "phuong")
    private String phuong;

    @Column(name = "tinh")
    private String tinh;

    @Column(name = "ma_xa")
    private String maXa;

    @Column(name = "ma_phuong")
    private String maPhuong;

    @Column(name = "ma_tinh")
    private String maTinh;

    @Column(name = "trang_thai")
    private Boolean trangThai;
}
