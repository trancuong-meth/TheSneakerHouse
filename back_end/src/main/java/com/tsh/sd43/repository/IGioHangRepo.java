package com.tsh.sd43.repository;

import com.tsh.sd43.entity.GioHang;
import com.tsh.sd43.entity.GioHangChiTiet;
import com.tsh.sd43.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface IGioHangRepo extends JpaRepository<GioHang, Long> {

    @Query(value = """
            select * from gio_hang where id_khach_hang is null
    """, nativeQuery = true)
    ArrayList<GioHang> getGioHangKhongDangNhap();

    @Query(value = """
            select * from gio_hang where id_khach_hang = :id
    """, nativeQuery = true)
    ArrayList<GioHang> getCartByIdKhachHang(@Param("id") Long id);

}
