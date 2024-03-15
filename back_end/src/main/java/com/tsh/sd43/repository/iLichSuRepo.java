package com.tsh.sd43.repository;


import com.tsh.sd43.entity.LichSu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface iLichSuRepo extends JpaRepository<LichSu, Long> {

    @Query(value = """
            select * from lich_su where id_hoa_don = :id
            """, nativeQuery = true)
    ArrayList<LichSu> findAllByIdHoaDon(@Param("id") Long id);
}
