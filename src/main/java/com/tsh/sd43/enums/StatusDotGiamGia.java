package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusDotGiamGia {

    CON_HAN(0),
    HET_HAN(1);

    private final Integer trangThai;

    StatusDotGiamGia(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
