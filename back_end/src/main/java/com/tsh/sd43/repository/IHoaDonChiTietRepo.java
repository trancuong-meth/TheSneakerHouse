package com.tsh.sd43.repository;

import com.tsh.sd43.entity.HoaDonChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IHoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Long> {

    @Query(value = """
        select * from hoa_don_chi_tiet where id_hoa_don = :id 
    """, nativeQuery = true)
    Page<HoaDonChiTiet> findBillDetailByIdBill(Pageable pageable, @Param("id")Long id);

    @Query(value = """
        select * from hoa_don_chi_tiet where id_hoa_don = :id 
    """, nativeQuery = true)
    ArrayList<HoaDonChiTiet> findBillDetailsByIdBill(@Param("id")Long id);

    @Modifying
    @Transactional
    @Query(value = """
        delete from hoa_don_chi_tiet where id = :id
    """, nativeQuery = true)
    void deleteById(@Param("id")Long id);
}
