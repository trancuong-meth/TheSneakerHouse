package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.ThuongHieu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequest {

    Long id;

    String ten;

    ThuongHieu idThuongHieu;

    TheLoai idTheLoai;

    Boolean trangThai;
}
