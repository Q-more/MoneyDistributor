package com.mediatoolkit.moneydistributor.api.service;

import com.mediatoolkit.moneydistributor.api.exceptions.UserException;
import com.mediatoolkit.moneydistributor.api.exceptions.enums.ApiErrorCode;
import com.mediatoolkit.moneydistributor.api.model.UserDto;
import com.mediatoolkit.moneydistributor.api.persistence.UserDao;
import com.mediatoolkit.moneydistributor.api.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lucija
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserDao userDao;

    public List<UserDto> findAll() {
        return userDao.findAll().stream()
            .map(u -> new UserDto(u.getId(), u.getFirstName(), u.getLastName(), u.getUsername(), u.getEmail()))
            .collect(Collectors.toList());
    }

    public UserDto getUser(Long id) {
        UserEntity user = userDao.getUser(id);

        if (user == null) {
            throw new UserException(ApiErrorCode.NOT_EXISTS, "User with given id dose not exists.");
        }

        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
    }

    public Long saveUser(UserDto userDto) {
        try {
            return userDao.saveUser(
                new UserEntity(
                    userDto.getId(),
                    userDto.getFirstName(),
                    userDto.getLastName(),
                    userDto.getUsername(),
                    userDto.getEmail()))
                .getId();
        } catch (DataIntegrityViolationException ex) {
            throw new UserException(ApiErrorCode.ALREADY_EXISTS, "Username or email already exists");
        }
    }

    public boolean exists(Long id) {
        return userDao.getUser(id) != null;
    }
}
