package com.tsh.sd43.repository;

import com.tsh.sd43.entity.DotGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DotGiamGiaRepo extends JpaRepository<DotGiamGia, Long> {
}
