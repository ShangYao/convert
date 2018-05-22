package com.jinanlongen.sparrow.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.jinanlongen.sparrow.domain.Merchandise;

public interface MerchandiseRep
    extends JpaRepository<Merchandise, Long>, JpaSpecificationExecutor<Merchandise> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE MERCHANDISES SET REVIEW_NEEDED=TRUE,STATE=? WHERE ID=?",
      nativeQuery = true)
  void updateSateBuId(String state, Long id);

  @Transactional
  @Modifying
  @Query(value = "UPDATE MERCHANDISES SET STATE=? WHERE ID=?", nativeQuery = true)
  void updateSateById(String state, Long id);

  @Transactional
  @Modifying
  @Query(value = "UPDATE MERCHANDISES SET REVIEW_NEEDED=?,STATE=? WHERE ID=?", nativeQuery = true)
  void updateSate(boolean needed, String state, Long id);

  @Transactional
  @Modifying
  @Query(value = "UPDATE MERCHANDISES SET MAINTAIN_AT=? WHERE ID=?", nativeQuery = true)
  void updateMaintainTime(Date timme, Long id);

  @Transactional
  @Modifying
  @Query(value = "UPDATE MERCHANDISES SET UPDATED_AT=? WHERE ID=?", nativeQuery = true)
  void updateTime(Date timme, Long id);

  @Query(value = "SELECT COUNT(*) FROM MERCHANDISES WHERE ITEM_ID=?  AND STATE NOT IN('回收站','草稿') ",
      nativeQuery = true)
  int itemCount(String itemId);

  @Query(
      value = "SELECT COUNT(*) FROM MERCHANDISES WHERE ITEM_ID=? AND ID!=? AND STATE NOT IN('回收站','草稿') ",
      nativeQuery = true)
  int itemCount2(String itemId, long id);

  @Query(value = "SELECT DISTINCT ID FROM MERCHANDISES  ", nativeQuery = true)
  List<Long> findId();

  @Query(value = "SELECT DISTINCT ID FROM MERCHANDISES WHERE ITEM_ID=? LIMIT 1 ",
      nativeQuery = true)
  Long findIdByItemId(String id);

  // Merchandise findFirst1ByItemIdAndState(String id, String state);
}
