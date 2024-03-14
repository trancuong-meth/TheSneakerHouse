package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.HoaDon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRequest {

    private Integer trangThai;

    private HoaDon hoaDon;

    private String ghiChu;
}
