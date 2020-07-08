package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserDao  {

	@PersistenceContext
	private EntityManager entityManager;

	public void deleteUser(final UserEntity userEntity) {
		entityManager.remove(userEntity);
	}

	public UserEntity getUserByUuid(final String uuid) {
		try {
			return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", uuid)
					.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public UserAuthEntity getUserAuth(final String accessToken) {
		try {
			return entityManager.createNamedQuery("userAuthByAccessToken", UserAuthEntity.class)
					.setParameter("accessToken", accessToken).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}
	
}
