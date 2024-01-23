package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum RolesNhanVien {

    ADMIN(0),
    NHAN_VIEN(1);

    private final Integer chucVu;

    RolesNhanVien(Integer chucVu) {
        this.chucVu = chucVu;
    }

}
