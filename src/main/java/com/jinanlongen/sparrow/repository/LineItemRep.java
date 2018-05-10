package com.jinanlongen.sparrow.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jinanlongen.sparrow.domain.LineItem;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年2月25日
 */
public interface LineItemRep extends JpaRepository<LineItem, Long> {
	@Transactional
	@Modifying
	void deleteByMId(long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE LINE_ITEMS SET SALES_STATE='已下架' WHERE M_ID=?", nativeQuery = true)
	void updateState(long id);

	@Query(value = "SELECT DISTINCT ID FROM LINE_ITEMS WHERE M_ID=?", nativeQuery = true)
	List<BigInteger> findIdByMId(long id);

	@Query(value = "SELECT * FROM LINE_ITEMS WHERE M_ID=?", nativeQuery = true)
	Set<LineItem> findByMId(long id);

	@Query(value = "SELECT COUNT(*) FROM LINE_ITEMS WHERE M_ID=? AND COLOR=? AND SIZE=? AND ID!=?", nativeQuery = true)
	int count(long mid, String color, String size, long id);

	@Query(value = "SELECT COUNT(*) FROM LINE_ITEMS WHERE M_ID=? AND COLOR=? AND SIZE=? ", nativeQuery = true)
	int count2(long mid, String color, String size);

	@Query(value = "SELECT COUNT(*) FROM LINE_ITEMS WHERE M_ID=? AND SALES_STATE='在售中' ", nativeQuery = true)
	int count3(long mid);

	@Query(value = "SELECT COUNT(*) FROM LINE_ITEM_ROC_IDS WHERE ROC_IDS=? AND LINE_ITEM_ID IN(SELECT ID FROM LINE_ITEMS WHERE M_ID IN (SELECT ID FROM MERCHANDISES WHERE STORE_ID IN(SELECT STORE_ID FROM MERCHANDISES WHERE ID=?)) ) ", nativeQuery = true)
	int countRocid(String rocid, long mid);

	@Query(value = "SELECT COUNT(*) FROM LINE_ITEM_ROC_IDS WHERE ROC_IDS=? AND LINE_ITEM_ID IN(SELECT ID FROM LINE_ITEMS WHERE M_ID IN (SELECT ID FROM MERCHANDISES WHERE STORE_ID IN(SELECT STORE_ID FROM MERCHANDISES WHERE ID=?)) AND  LINE_ITEM_ID!=?) ", nativeQuery = true)
	int countRocid2(String rocid, long mid, long itemid);
}
