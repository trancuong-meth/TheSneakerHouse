package com.tsh.sd43.repository;

import com.tsh.sd43.entity.ThuongHieu;
import com.tsh.sd43.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IThuongHieuRepo extends JpaRepository<ThuongHieu, Long> {

    @Query( value = """
        select * from thuong_hieu v
        WHERE ten like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<ThuongHieu> findBrandsByStateAndName(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from thuong_hieu v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<ThuongHieu> findBrandsById(@Param("id") Long id);

    @Query(value = """
        select * from thuong_hieu v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<ThuongHieu> findBrandsByName(@Param("ten") String ten);

    ArrayList<ThuongHieu> findByOrderByNgayTaoDesc();

}
