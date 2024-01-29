package com.tsh.sd43.repository;

import com.tsh.sd43.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISanPhamRepo extends JpaRepository<SanPham, Long> {
}
