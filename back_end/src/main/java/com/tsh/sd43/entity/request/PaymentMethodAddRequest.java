package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.HoaDon;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentMethodAddRequest {

    private Integer loaiThanhToan;

    private BigDecimal soTienThanhToan;

    private String ghiChu;

    private HoaDon idHoaDon;

    private Boolean deleted;
}
