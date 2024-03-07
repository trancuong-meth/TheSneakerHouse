package com.tsh.sd43.entity.request;

import com.tsh.sd43.entity.SanPhamChiTiet;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageAddRequest {

    private Long idSanPhamChiTiet;

    private String duongDan;

}