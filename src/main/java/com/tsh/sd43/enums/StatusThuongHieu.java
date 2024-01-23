package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusThuongHieu {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusThuongHieu(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
