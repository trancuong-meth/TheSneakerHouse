package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusKhachHang {

    DA_DANG_KY(0),
    CHUA_DANG_KY(1);

    private final Integer trangThai;

    StatusKhachHang(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
