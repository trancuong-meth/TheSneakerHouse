package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusHinhAnh {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusHinhAnh(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
