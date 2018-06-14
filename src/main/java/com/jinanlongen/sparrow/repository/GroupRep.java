package com.jinanlongen.sparrow.repository;

import java.math.BigInteger;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.jinanlongen.sparrow.domain.Group;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月1日
 */
public interface GroupRep extends JpaRepository<Group, Long> {
  @Transactional
  void deleteByParentId(String id);

  List<Group> findByParentIdIsNull();

  List<Group> findByParentId(String id);

  @Query(value = "SELECT COUNT(*) FROM GROUPS_USERS WHERE GROUP_ID=?", nativeQuery = true)
  int findGroupUser(long id);

  @Query(value = "SELECT COUNT(*) FROM GROUPS_STORES WHERE GROUP_ID=?", nativeQuery = true)
  int findGroupStore(long id);

  @Query(value = "SELECT COUNT(*) FROM GROUPS WHERE NAME=?", nativeQuery = true)
  int countGroupName(String name);

  @Query(value = "SELECT COUNT(*) FROM GROUPS WHERE NAME=? AND ID!=?", nativeQuery = true)
  int countGroupName2(String name, Long id);

  @Query(
      value = " SELECT DISTINCT UID FROM GROUPS_USERS WHERE GROUP_ID IN(SELECT ID FROM GROUPS A,(SELECT GROUP_ID FROM GROUPS_USERS WHERE UID=?)B WHERE A.ID=B.GROUP_ID OR TO_NUMBER( A.PARENT_ID,'99')=B.GROUP_ID) ",
      nativeQuery = true)
  List<BigInteger> getUidsByGid(long reviewerId);
}
