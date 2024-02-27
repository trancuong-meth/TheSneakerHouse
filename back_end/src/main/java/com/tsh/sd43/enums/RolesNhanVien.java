package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum RolesNhanVien {

    ADMIN(true),
    NHAN_VIEN(false);

    private final Boolean chucVu;

    RolesNhanVien(Boolean chucVu) {
        this.chucVu = chucVu;
    }
}
