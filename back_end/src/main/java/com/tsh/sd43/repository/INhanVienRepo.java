package com.tsh.sd43.repository;

import com.tsh.sd43.entity.KhachHang;
import com.tsh.sd43.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface INhanVienRepo extends JpaRepository<NhanVien, Long> {

    @Query( value = """
        select * from nhan_vien v
        WHERE trang_thai like :trangThai 
        AND (ma like :key
        OR ten like :key)
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<NhanVien> findEmployees(Pageable pageable, @Param("trangThai") String trangThai, @Param("key") String key);

    @Query(value = """
        select * from nhan_vien v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<NhanVien> findEmployeeByID(@Param("id") Long id);

    @Query(value = """
        select top 1 ma from nhan_vien order by ma desc
    """, nativeQuery = true)
    String generateNewestCode();
}
