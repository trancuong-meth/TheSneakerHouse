package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusTheLoai {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusTheLoai(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
