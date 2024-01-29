package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusVoucher {

    CON_HAN(true),
    HET_HAN(false);

    private final Boolean trangThai;

    StatusVoucher(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
