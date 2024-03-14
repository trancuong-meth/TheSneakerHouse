package com.tsh.sd43.entity;

import com.tsh.sd43.entity.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lich_su")
public class LichSu extends PrimaryEntity {

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon idHoaDon;
}
