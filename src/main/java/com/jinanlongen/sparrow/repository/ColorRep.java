package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jinanlongen.sparrow.domain.Color;

public interface ColorRep extends JpaRepository<Color, Long> {
  @Query(value = "SELECT COUNT(*) FROM COLORS WHERE NAME=? AND MERCHANDISE_ID=? ",
      nativeQuery = true)
  int countColor(String name, long merchandiseId);

  @Query(value = "SELECT COUNT(*) FROM COLORS WHERE NAME=? AND MERCHANDISE_ID=? AND ID !=?",
      nativeQuery = true)
  int countColor2(String name, long merchandiseId, long id);


  List<Color> findByMerchandiseId(long mid);

  @Query(value = "SELECT NAME FROM  COLORS WHERE ID=?", nativeQuery = true)
  String findNameById(long id);
}
