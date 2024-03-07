package com.tsh.sd43.repository;

import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ISanPhamRepo extends JpaRepository<SanPham, Long> {

    @Query( value = """
        select * from san_pham v
        WHERE ten like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPham> findPanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from san_pham v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductById(@Param("id") Long id);

    @Query(value = """
        select * from san_pham v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductByName(@Param("ten") String ten);

    ArrayList<SanPham> findByOrderByNgayTaoDesc();

    @Query(value = """
        select top 1 ma from san_pham order by ma desc
    """, nativeQuery = true)
    String generateNewestCode();
}
