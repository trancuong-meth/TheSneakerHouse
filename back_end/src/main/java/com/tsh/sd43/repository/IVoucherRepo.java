package com.tsh.sd43.repository;

import com.tsh.sd43.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IVoucherRepo extends JpaRepository<Voucher, Long> {

    @Query( value = """
        select * from Voucher v
    """, nativeQuery = true)
    Page<Voucher> findVouchersByState(Pageable pageable);

    @Query(value = """
        select * from Voucher v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<Voucher> findVoucherByIdAndState(@Param("id") Long id);

    @Query(value = """
        select top 1 ma from voucher order by ma desc
    """, nativeQuery = true)
    String generateNewestCode();
}
