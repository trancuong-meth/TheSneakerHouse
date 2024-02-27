package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum RolesKhachHang {

    KHACH_LE(true),
    kHACH_HANG(false);

    private final Boolean loaiKhach;

    RolesKhachHang(Boolean loaiKhach) {
        this.loaiKhach = loaiKhach;
    }
}
