package com.jinanlongen.sparrow.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.jinanlongen.sparrow.domain.SourceUrl;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月31日
 */
public interface SourceUrlRep extends JpaRepository<SourceUrl, Long> {
  @Transactional
  @Modifying
  @Query(value = "update source_url set state=0 where id=?", nativeQuery = true)
  void updateState(long id);

  List<SourceUrl> findByMerchandiseIdAndState(long id, int state);

  List<SourceUrl> findByMerchandiseId(long id);
}
