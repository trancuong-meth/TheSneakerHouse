package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusKichCo {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusKichCo(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
