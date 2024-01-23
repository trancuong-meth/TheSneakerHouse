package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusVanChuyen {

    DANG_GIAO(0),
    DA_THANH_CONG(1);

    private final Integer trangThai;

    StatusVanChuyen(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
