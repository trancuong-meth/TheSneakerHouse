package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusBinhLuan {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusBinhLuan(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
