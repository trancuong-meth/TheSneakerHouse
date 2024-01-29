package com.tsh.sd43.repository;

import com.tsh.sd43.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheLoaiRepo extends JpaRepository<TheLoai, Long> {
}
