package com.tsh.sd43.entity.base;

import com.tsh.sd43.infrastructure.listener.PrimaryEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(PrimaryEntityListener.class)
public class PrimaryEntity {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    private Date ngayCapNhat;

    @Column(name = "deleted")
    private Boolean deleted;
}
