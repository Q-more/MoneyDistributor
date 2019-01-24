package com.mediatoolkit.moneydistributor.api.web;

import com.mediatoolkit.moneydistributor.api.model.UserDto;
import com.mediatoolkit.moneydistributor.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        LOG.info("Getting all users.");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        LOG.info("Getting user with id " + id);
        UserDto user = userService.getUser(id);

        LOG.debug("User " + user);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Long> saveUser(@RequestBody @Valid UserDto userDto) {
        LOG.info("Saving user.");
        Long id = userService.saveUser(userDto);

        LOG.info("User saved.");
        LOG.debug("User " + userDto);

        return ResponseEntity.ok(id);
    }
}
