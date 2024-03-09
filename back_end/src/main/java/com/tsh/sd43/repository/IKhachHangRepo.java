package com.tsh.sd43.repository;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IKhachHangRepo extends JpaRepository<KhachHang, Long> {

    @Query( value = """
        select * from khach_hang v
        WHERE trang_thai like :trangThai 
        AND (ma like :key
        OR ten like :key)
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<KhachHang> findCustomers(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
        select * from khach_hang v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<KhachHang> findKhachHangById(@Param("id") Long id);

    @Query(value = """
        select top 1 ma from khach_hang order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();
}
