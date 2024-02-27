package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusGioHang {

    TRONG(true),
    KHONG_TRONG(false);

    private final Boolean trangThai;

    StatusGioHang(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
