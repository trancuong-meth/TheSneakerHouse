package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusNhanVien {

    DANG_HOAT_DONG(true),
    KHONG_HOAT_DONG(false);

    private final Boolean trangThai;

    StatusNhanVien(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
