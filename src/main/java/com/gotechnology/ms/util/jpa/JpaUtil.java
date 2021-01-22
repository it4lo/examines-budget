package com.gotechnology.ms.util.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("budget_ms");

	}

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
