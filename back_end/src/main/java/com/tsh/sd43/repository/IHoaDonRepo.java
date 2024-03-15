package com.tsh.sd43.repository;

import com.tsh.sd43.entity.HoaDon;
import com.tsh.sd43.entity.responce.BillStateResponce;
import com.tsh.sd43.entity.responce.ProductBestSellerResponce;
import com.tsh.sd43.entity.responce.RevenueRangeDateResponce;
import com.tsh.sd43.entity.responce.RevenueResponce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.ArrayList;
import java.util.Date;

public interface IHoaDonRepo extends JpaRepository<HoaDon, Long> {

    @Query(value = """
            select * from hoa_don where trang_thai = :trangThai and deleted = 1
    """, nativeQuery = true)
    ArrayList<HoaDon> getHoaDonByTrangThai(@Param("trangThai")Integer trangThai);

    @Query(value = """
        select top 1 ma from hoa_don order by ngay_tao desc
    """, nativeQuery = true)
    String generateNewestCode();

    @Modifying
    @Transactional
    @Query(value= """
        update hoa_don set deleted = 0 where id = :id
    """,nativeQuery = true)
    void deleteBillByIdBill(@Param("id") Long id);

    @Query(value = """
            select * from hoa_don where not trang_thai = 0 and deleted = 1 order by ngay_tao desc
    """, nativeQuery = true)
    Page<HoaDon> getAllBillPanigation(Pageable pageable);

    @Query(value = """
            select * from hoa_don where trang_thai = :trangThai and deleted = 1 order by ngay_tao desc
    """, nativeQuery = true)
    Page<HoaDon> getBillPanigationByState(Pageable pageable, @Param("trangThai")Integer trangThai);

    @Query(value = """
            select count(*) as so_luong, SUM(hd.tong_tien_sau_giam) as tong_tien from hoa_don as hd
            where deleted = 1 and not trang_thai in (0, 5) and MONTH(hd.ngay_tao) = MONTH(GETDATE())
    """, nativeQuery = true)
    RevenueResponce getRevenueMonth();

    @Query(value = """
            select count(*) as so_luong, SUM(hd.tong_tien_sau_giam) as tong_tien from hoa_don as hd
            where deleted = 1 and not trang_thai in (0, 5) and CAST( hd.ngay_tao AS Date ) = CAST( GETDATE() AS Date )
    """, nativeQuery = true)
    RevenueResponce getRevenueDay();

    @Query(value = """
            select sum(hdct.so_luong)  from hoa_don as hd
            join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
            where hd.deleted = 1 and not hd.trang_thai in (0, 5) and MONTH(hd.ngay_tao) = MONTH(GETDATE())
    """, nativeQuery = true)
    Integer getQuantityOfProductWithMonth();

    @Query(value = """
     select CAST( hd.ngay_tao AS Date ) as ngay ,count(*) as so_luong, SUM(hd.tong_tien_sau_giam) as tong_tien\s
     from hoa_don as hd
     where deleted = 1 and not trang_thai in (0, 5)
     and( CAST( hd.ngay_tao AS Date ) > CAST( :start_date AS Date )
      and CAST( hd.ngay_tao AS Date ) <= CAST( :end_date AS Date )
      )
     group by CAST( hd.ngay_tao AS Date )
    """, nativeQuery = true)
    ArrayList<RevenueRangeDateResponce> getRevenueRangeDate(@Param("start_date")Date startDate,
                                                              @Param("end_date")Date endDate);

    @Query(value = """
     select top 5 sp.ten ,sum(hdct.so_luong) as so_luong, SUM(hd.tong_tien_sau_giam) as tong_tien
     from hoa_don as hd
     join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
     join san_pham_chi_tiet spct on spct.id = hdct.id_san_pham_chi_tiet
     join san_pham sp on sp.id = spct.id_san_pham
     where hd.deleted = 1 and not hd.trang_thai in (0, 5)
     group by sp.ten
     order by so_luong desc
    """, nativeQuery = true)
    ArrayList<ProductBestSellerResponce> getTop5ProductBestSeller();

    @Query(value = """
         select hd.trang_thai,sum(hdct.so_luong) as so_luong
    	 from hoa_don as hd
    	 join hoa_don_chi_tiet hdct on hdct.id_hoa_don = hd.id
    	 where hd.deleted = 1 and not hd.trang_thai in (0, 5)
    	 group by hd.trang_thai
    	 order by so_luong desc
    """, nativeQuery = true)
    ArrayList<BillStateResponce> getBillState();
}
