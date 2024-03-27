package com.tsh.sd43.repository;

import com.tsh.sd43.entity.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IHinhThucThanhToanRepo extends JpaRepository<HinhThucThanhToan, Long> {

    @Query(value = """
        select * from hinh_thuc_thanh_toan where id_hoa_don = :id
    """, nativeQuery = true)
    ArrayList<HinhThucThanhToan> getAllPaymentMethodByIdBill(@Param("id")Long id);
}
