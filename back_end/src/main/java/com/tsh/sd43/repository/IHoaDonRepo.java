package com.tsh.sd43.repository;

import com.tsh.sd43.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.ArrayList;

public interface IHoaDonRepo extends JpaRepository<HoaDon, Long> {

    @Query(value = """
            select * from hoa_don where trang_thai = :trangThai and deleted = 1
    """, nativeQuery = true)
    ArrayList<HoaDon> getHoaDonByTrangThai(@Param("trangThai")Integer trangThai);

    @Query(value = """
        select top 1 ma from hoa_don order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();

    @Modifying
    @Transactional
    @Query(value= """
        update hoa_don set deleted = 0 where id = :id
    """,nativeQuery = true)
    void deleteBillByIdBill(@Param("id") Long id);
}
