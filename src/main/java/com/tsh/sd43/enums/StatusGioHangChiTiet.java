package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusGioHangChiTiet {

    TRONG(0),
    KHONG_TRONG(1);

    private final Integer trangThai;

    StatusGioHangChiTiet(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
