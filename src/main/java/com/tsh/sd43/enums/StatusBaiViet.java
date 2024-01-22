package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusBaiViet {

    HIEN_THI(0),
    AN(1);

    private final Integer trangThai;

    StatusBaiViet(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
