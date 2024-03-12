package com.tsh.sd43.repository;

import com.tsh.sd43.entity.SanPham;
import com.tsh.sd43.entity.SanPhamChiTiet;
import com.tsh.sd43.entity.TheLoai;
import com.tsh.sd43.entity.responce.ProductResponce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ISanPhamRepo extends JpaRepository<SanPham, Long> {

    @Query( value = """
        select sp.id, sp.ten, sp.mo_ta, tl.ten as 'ten_the_loai',
          th.ten as 'ten_thuong_hieu',
          sum(spct.so_luong_ton) as 'so_luong',
          sp.trang_thai
          from san_pham sp
          join the_loai tl on tl.id = sp.id_the_loai
          join thuong_hieu th on th.id = sp.id_thuong_hieu
          join san_pham_chi_tiet spct on spct.id_san_pham = sp.id
          WHERE sp.ten like :key
          group by sp.id,sp.id, sp.ten, sp.mo_ta, tl.ten, th.ten, sp.trang_thai
    """, nativeQuery = true)
    Page<ProductResponce> findPanigation(Pageable pageable, @Param("key") String key);

    @Query(value = """
        select * from san_pham v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductById(@Param("id") Long id);

    @Query(value = """
        select * from san_pham v
        where v.ten = :ten 
    """, nativeQuery = true)
    ArrayList<SanPham> findProductByName(@Param("ten") String ten);

    ArrayList<SanPham> findByOrderByNgayTaoDesc();

    @Query(value = """
        select top 1 ma from san_pham order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();

}
