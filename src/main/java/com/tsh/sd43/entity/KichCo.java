package com.tsh.sd43.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "KichCo")
public class KichCo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdMauSac")
    private Long idMauSac;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NgayTao")
    private String ngayTao;

    @Column(name = "NgayCapNhat")
    private String ngayCapNhat;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
