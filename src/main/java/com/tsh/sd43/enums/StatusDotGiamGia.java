package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusDotGiamGia {

    CON_HAN(true),
    HET_HAN(false);

    private final Boolean trangThai;

    StatusDotGiamGia(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
