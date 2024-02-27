package com.tsh.sd43.repository;

import com.tsh.sd43.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INhanVienRepo extends JpaRepository<NhanVien, Long> {
}
