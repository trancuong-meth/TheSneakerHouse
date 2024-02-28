package com.tsh.sd43.entity.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaleAddRequest {

    private String ma;

    private String ten;

    private Float phanTramGiam;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer trangThai;
}
