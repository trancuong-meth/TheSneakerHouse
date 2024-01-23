package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusGioHang {

    TRONG(0),
    KHONG_TRONG(1);

    private final Integer trangThai;

    StatusGioHang(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
