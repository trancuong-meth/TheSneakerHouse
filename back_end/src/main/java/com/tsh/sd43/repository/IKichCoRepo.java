package com.tsh.sd43.repository;

import com.tsh.sd43.entity.KichCo;
import com.tsh.sd43.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IKichCoRepo extends JpaRepository<KichCo, Long> {

    @Query( value = """
        select * from kich_co v
        WHERE kich_co like :key
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<KichCo> findSizePanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from kich_co v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<KichCo> findSizeById(@Param("id") Long id);

    @Query(value = """
        select * from kich_co v
        where v.kich_co = :kich_co 
    """, nativeQuery = true)
    ArrayList<KichCo> findSizeBySize(@Param("kich_co") Integer kichCo);

}
