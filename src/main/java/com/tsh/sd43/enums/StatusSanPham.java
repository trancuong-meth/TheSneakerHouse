package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusSanPham {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusSanPham(Integer trangThai) {
        this.trangThai = trangThai;
    }

}