package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.jinanlongen.sparrow.domain.Album;

public interface AlbumRep extends JpaRepository<Album, Long> {
  List<Album> findByMerchandiseId(long id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE ALBUM SET NAME=? WHERE ID=?", nativeQuery = true)
  void updateName(String name, long id);

  @Query(value = "SELECT NAME FROM  ALBUM WHERE ID=?", nativeQuery = true)
  String findNameById(long id);

}
