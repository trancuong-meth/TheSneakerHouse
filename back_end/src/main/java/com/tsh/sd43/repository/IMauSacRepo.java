package com.tsh.sd43.repository;

import com.tsh.sd43.entity.MauSac;
import com.tsh.sd43.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IMauSacRepo extends JpaRepository<MauSac, Long> {


    @Query( value = """
        select * from mau_sac v
        WHERE ten like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<MauSac> findColorPanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from mau_sac v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<MauSac> findColorById(@Param("id") Long id);

    @Query(value = """
        select * from mau_sac v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<MauSac> findColorByName(@Param("ten") String ten);

}
