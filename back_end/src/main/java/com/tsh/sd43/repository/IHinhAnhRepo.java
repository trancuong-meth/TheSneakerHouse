package com.tsh.sd43.repository;

import com.tsh.sd43.entity.HinhAnh;
import com.tsh.sd43.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IHinhAnhRepo extends JpaRepository<HinhAnh, Long> {

    @Query( value = """
        select * from hinh_anh v
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<HinhAnh> findPanigation(Pageable pageable);

    @Query(value = """
        select * from hinh_anh v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<HinhAnh> findImageById(@Param("id") Long id);

    ArrayList<HinhAnh> findByOrderByNgayTaoDesc();
}
