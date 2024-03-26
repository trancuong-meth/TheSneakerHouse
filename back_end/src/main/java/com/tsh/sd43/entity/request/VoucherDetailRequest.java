package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.Voucher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDetailRequest {

    private Voucher voucher;

    private KhachHang khachHang;
}
