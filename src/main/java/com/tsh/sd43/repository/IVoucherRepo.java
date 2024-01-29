package com.tsh.sd43.repository;

import com.tsh.sd43.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVoucherRepo extends JpaRepository<Voucher, Long> {
}
