package com.jinanlongen.sparrow.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月5日
 */
@Service
public class BaseNativeSqlRepository {
	@PersistenceUnit
	private EntityManagerFactory emf;

	public List<Object[]> sqlArrayList(String sql) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.getResultList();
		em.close();
		return list;
	}

}
