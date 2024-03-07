package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.ThuongHieu;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {

    ThuongHieu idThuongHieu;

    TheLoai idTheLoai;

    String ten;

    String moTa;
}
