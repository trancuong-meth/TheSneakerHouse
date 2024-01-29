package com.tsh.sd43.repository;

import com.tsh.sd43.entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepo extends JpaRepository<KichCo, Long> {
}
