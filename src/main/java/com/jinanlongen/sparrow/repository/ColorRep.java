package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jinanlongen.sparrow.domain.Color;

public interface ColorRep extends JpaRepository<Color, Long> {
  @Query(value = "select count(*) from colors where name=? and merchandise_id=? ",
      nativeQuery = true)
  int countColor(String name, long merchandiseId);

  @Query(value = "select count(*) from colors where name=? and merchandise_id=? and id !=?",
      nativeQuery = true)
  int countColor2(String name, long merchandiseId, long id);


  List<Color> findByMerchandiseId(long mid);

  @Query(value = "select name from  colors WHERE ID=?", nativeQuery = true)
  String findNameById(long id);
}
