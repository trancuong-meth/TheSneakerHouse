package com.tsh.sd43.repository;

import com.tsh.sd43.entity.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChucVuRepo extends JpaRepository<ChucVu, Long> {

    ChucVu findByMa(String ma);
}
