package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "the_loai")
public class TheLoai extends PrimaryEntity {

    @Column(name = "ten")
    private String ten;

}
