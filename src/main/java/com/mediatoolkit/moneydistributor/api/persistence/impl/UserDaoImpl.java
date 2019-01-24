package com.mediatoolkit.moneydistributor.api.persistence.impl;

import com.mediatoolkit.moneydistributor.api.persistence.UserDao;
import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;
import com.mediatoolkit.moneydistributor.api.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public UserEntity getUser(String email) {
        return userRepository.getByEmail(email);
    }

}
