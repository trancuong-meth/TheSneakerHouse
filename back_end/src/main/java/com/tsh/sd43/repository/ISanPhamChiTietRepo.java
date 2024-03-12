package com.tsh.sd43.repository;

import com.tsh.sd43.entity.SanPhamChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ISanPhamChiTietRepo extends JpaRepository<SanPhamChiTiet, Long> {

    @Query( value = """
        select * from san_pham_chi_tiet v
        where id_san_pham = :id
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> findPanigation(Pageable pageable, @Param("id") Long id);

    @Query(value = """
        select * from san_pham_chi_tiet v
        where v.id = :id 
    """, nativeQuery = true)
    ArrayList<SanPhamChiTiet> findProductDetailById(@Param("id") Long id);

    ArrayList<SanPhamChiTiet> findByOrderByNgayTaoDesc();

    @Query( value = """
        select * from san_pham_chi_tiet v
        ORDER BY ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> findAllAndPanigation(Pageable pageable);

    @Query(value = """  
            select spct.* from san_pham_chi_tiet spct
            join san_pham sp on sp.id = spct.id_san_pham
            where sp.id in :ids
            and id_mau_sac like :idMauSac
            and id_kich_co like :idKichCo 
            and sp.id_the_loai like :idTheLoai 
            and sp.ten like :key
            ORDER BY spct.ngay_tao DESC
    """, nativeQuery = true)
    Page<SanPhamChiTiet> getProductDetailByIds(@Param("ids") List<Integer> ids,
                                               @Param("idMauSac") String idMauSac,
                                               @Param("idKichCo") String idKichCo,
                                               @Param("idTheLoai") String idTheLoai,
                                               @Param("key") String key,
                                               Pageable pageable);

}
