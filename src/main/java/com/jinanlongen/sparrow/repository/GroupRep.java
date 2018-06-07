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
      value = " select distinct uid from groups_users where group_id in(select group_id from groups_users a,groups b where (a.group_id=b.id or a.group_id=to_number(b.parent_id,'99')) and uid=?) ",
      nativeQuery = true)
  List<BigInteger> getUidsByGid(long reviewerId);
}
