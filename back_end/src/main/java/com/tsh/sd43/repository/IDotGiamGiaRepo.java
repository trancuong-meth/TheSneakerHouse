package com.tsh.sd43.repository;

import com.tsh.sd43.entity.DotGiamGia;
import com.tsh.sd43.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IDotGiamGiaRepo extends JpaRepository<DotGiamGia, Long> {


    @Query( value = """
        select * from dot_giam_gia v
        WHERE trang_thai like :trangThai 
        AND (ma like :key
        OR ten like :key)
    """, nativeQuery = true)
    Page<Voucher> findSalesByState(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
        select * from dot_giam_gia v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<DotGiamGia> findSalesByIdAndState(@Param("id") Long id);

    @Query(value = """
        select top 1 ma from dot_giam_gia order by ma desc
    """, nativeQuery = true)
    String generateNewestCode();
}
