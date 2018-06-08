package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jinanlongen.sparrow.domain.Size;

public interface SizeRep extends JpaRepository<Size, Long> {
  @Query(value = "SELECT COUNT(*) FROM SIZES WHERE NAME=? AND MERCHANDISE_ID=? ",
      nativeQuery = true)
  int countSize(String name, long merchandiseId);

  @Query(value = "SELECT COUNT(*) FROM SIZES WHERE NAME=? AND MERCHANDISE_ID=? AND ID !=?",
      nativeQuery = true)
  int countSize2(String name, long merchandiseId, long id);


  List<Size> findByMerchandiseId(long mid);

  @Query(value = "select name from  sizes WHERE ID=?", nativeQuery = true)
  String findNameById(long id);
}
