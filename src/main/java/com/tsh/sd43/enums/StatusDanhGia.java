package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusDanhGia {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusDanhGia(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
