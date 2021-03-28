package ru.igar15.rest_voting_system.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.service.UserService;

import static ru.igar15.rest_voting_system.util.ValidationUtil.assureIdConsistent;
import static ru.igar15.rest_voting_system.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    static final String REST_URL = "/rest/profile";

    @Autowired
    private UserService service;

    @GetMapping
    public User get() {
        int userId = authUserId();
        log.info("get {}", userId);
        return service.get(userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        int userId = authUserId();
        log.info("delete {}", userId);
        service.delete(userId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        int userId = authUserId();
        log.info("update {} with userId={}", user, userId);
        assureIdConsistent(user, userId);
        service.update(user);
    }
}