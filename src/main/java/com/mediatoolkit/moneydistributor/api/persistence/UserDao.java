package com.mediatoolkit.moneydistributor.api.persistence;

import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;

import java.util.List;

/**
 * @author lucija
 */
public interface UserDao {

    List<UserEntity> findAll();

    UserEntity getUser(Long id);

    void deleteUser(Long id);

    UserEntity saveUser(UserEntity user);

    void updateUser(UserEntity user);

    UserEntity getUser(String email);
}
