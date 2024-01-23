package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusSanPhamChiTiet {

    CON_HANG(0),
    HET_HANG(1);

    private final Integer trangThai;

    StatusSanPhamChiTiet(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
