package com.tsh.sd43.enums;

import lombok.Getter;

@Getter
public enum StatusNhanVien {

    LAM_VIEC(0),
    NGHI_VIEC(1);

    private final Integer trangThai;

    StatusNhanVien(Integer trangThai) {
        this.trangThai = trangThai;
    }

}
