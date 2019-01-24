package com.mediatoolkit.moneydistributor.api.persistence.repository;

import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByEmail(String email);
}
