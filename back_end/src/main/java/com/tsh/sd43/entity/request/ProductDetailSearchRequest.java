package com.tsh.sd43.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailSearchRequest {

    String ids;

    Integer pageNo;

    Integer pageSize;

    String key;

    Long idMauSac;

    Long idTheLoai;

    Long idKichCo;
}
