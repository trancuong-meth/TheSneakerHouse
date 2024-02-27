package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusTheLoai {

    HIEN_THI(true),
    AN(false);

    private final Boolean trangThai;

    StatusTheLoai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

}
