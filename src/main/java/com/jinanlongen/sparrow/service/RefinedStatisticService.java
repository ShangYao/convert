package com.jinanlongen.sparrow.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jinanlongen.sparrow.domain.Merchandise;
import com.jinanlongen.sparrow.domain.Statistics;
import com.jinanlongen.sparrow.repository.BaseNativeSqlRepository;
import com.jinanlongen.sparrow.util.DateUtils;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月22日
 */
@Service
public class RefinedStatisticService {
  @Autowired
  private BaseNativeSqlRepository sqlRep;

  public Merchandise staQuery(Merchandise merchandise) {
    return statistics(merchandise);
  }

  private Merchandise statistics(Merchandise merchandise) {
    // StringBuffer storeSql = new StringBuffer(
    // "SELECT distinct D.NAME,C.B FROM (SELECT STORE_ID A,COUNT(*) B FROM MERCHANDISES WHERE 1=1
    // AND STATE NOT IN('回收站','草稿') ");
    StringBuffer userSql = new StringBuffer(
        "select  D.USER_NAME ,C.B COUNT FROM (SELECT OWNER_ID A,COUNT(*) B FROM MERCHANDISES WHERE 1=1 AND STATE NOT IN('回收站','草稿')  ");
    String auidtSql =
        "SELECT USER_NAME,SUCESS,PASS FROM (SELECT ID,SUCESS,PASS FROM (SELECT CREATED_USER_ID ID,COUNT(*) SUCESS   FROM STATE_CHANGES WHERE 1=1 and 2=2 and OLD_STATE='待审核' AND NEW_STATE='已发布' GROUP BY CREATED_USER_ID)A,(SELECT CREATED_USER_ID ID2,COUNT(*) PASS FROM STATE_CHANGES WHERE 3=3 and 4=4 and OLD_STATE='待审核' AND NEW_STATE='审核未过' GROUP BY CREATED_USER_ID)B WHERE A.ID=B.ID2) F LEFT JOIN USERS ON F.ID=USERS.ID";

    if (0 != merchandise.getOwnerId()) {
      // storeSql.append(" AND OWNER_ID=" + merchandise.getOwnerId());
      userSql.append(" AND OWNER_ID=" + merchandise.getOwnerId());
    }
    if (StringUtils.isNotBlank(merchandise.getBeginCreated())) {
      // storeSql.append(" AND CREATED_AT>='"
      // + DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00") + "'");
      userSql.append(" AND CREATED_AT>='"
          + DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00") + "'");
      auidtSql = auidtSql.replace("1=1",
          "CREATED_AT>='" + DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00") + "'");
      auidtSql = auidtSql.replace("3=3",
          "CREATED_AT>='" + DateUtils.getDate(merchandise.getBeginCreated() + " 00-00-00") + "'");
    }
    if (StringUtils.isNotBlank(merchandise.getEndCreated())) {
      // storeSql.append(" AND CREATED_AT<='"
      // + DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59") + "'");
      userSql.append(" AND CREATED_AT<='"
          + DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59") + "'");
      auidtSql = auidtSql.replace("2=2",
          " CREATED_AT<='" + DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59") + "'");
      auidtSql = auidtSql.replace("4=4",
          " CREATED_AT<='" + DateUtils.getDate(merchandise.getEndCreated() + " 23-59-59") + "'");
    }
    // storeSql.append(
    // " GROUP BY STORE_ID)C RIGHT JOIN (SELECT A.NAME GNAME,A.PARENT_ID,A.ID GID,C.NAME,C.ID FROM
    // GROUPS A,GROUPS_STORES B ,STORES C WHERE A.ID=B.GROUP_ID AND B.STORE_ID=C.ID ");
    userSql.append(
        " GROUP BY OWNER_ID)C RIGHT  JOIN (SELECT  A.NAME GNAME,A.PARENT_ID,A.ID GID,C.USER_NAME,C.ID FROM GROUPS A,GROUPS_USERS B ,USERS C WHERE A.ID=B.GROUP_ID AND B.UID=C.ID AND A.IS_LEAF=TRUE");

    if (0 != merchandise.getOwnerId()) {
      userSql.append(" AND C.ID=" + merchandise.getOwnerId());
    }

    // storeSql.append(" ) D ON C.A=D.ID ORDER BY D.PARENT_ID,D.GID,D.NAME ");
    userSql.append(" ) D ON C.A=D.ID ORDER BY D.PARENT_ID,D.GID");

    // merchandise.setsSlist(query(storeSql.toString(), 2));
    merchandise.setUserSlist(query(userSql.toString(), 2));
    merchandise.setaSlist(query(auidtSql, 3));
    return merchandise;

  }

  private List<Statistics> query(String sql, int count) {
    List<Statistics> list = new ArrayList<>();
    List<Object[]> alist = sqlRep.sqlArrayList(sql);
    Statistics sta;
    for (Object[] objects : alist) {
      sta = new Statistics();
      sta.setName((String) objects[0]);
      if (null == objects[1]) {
        sta.setCount(0 + "");
      } else {
        sta.setCount(String.valueOf(objects[1]));
      }
      if (count == 3) {
        sta.setCount2(String.valueOf(objects[2]));
      }
      list.add(sta);
    }
    return list;
  }
}
