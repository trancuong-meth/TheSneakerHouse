package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "mau_sac")
public class MauSac extends PrimaryEntity {

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten_mau_sac")
    private String tenMauSac;

}
