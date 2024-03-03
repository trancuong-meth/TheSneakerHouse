package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "kich_co")
public class KichCo extends PrimaryEntity {

    @Column(name = "kich_co")
    private Integer kichCo;

}
