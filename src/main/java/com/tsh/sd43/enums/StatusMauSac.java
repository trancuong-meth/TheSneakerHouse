package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusMauSac {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusMauSac(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
