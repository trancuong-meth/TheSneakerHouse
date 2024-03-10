package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.Voucher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVoucherUpdateRequest {

    Voucher voucher;

    HoaDon hoaDon;
}
