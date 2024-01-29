package com.tsh.sd43.repository;

import com.tsh.sd43.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Long> {
}
