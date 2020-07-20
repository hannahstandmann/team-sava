package com.smbaiwsy.teamsava.service;

import com.smbaiwsy.teamsava.document.User;
import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.exception.UserNotFoundException;
import com.smbaiwsy.teamsava.repository.UserRepository;
import java.time.LocalDate;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void getSpecificUserSuccess() throws UserNotFoundException {

        LocalDate creationDate = LocalDate.now();
        LocalDate birthday = LocalDate.of(1974, 7, 28);

        User user1 = User.builder()
                .id("1")
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(birthday)
                .creationDate(creationDate)
                .build();

        Mockito.when(
                userRepository
                        .findById("1"))
                .thenReturn(Optional.of(user1));
        UserDto response = null;
        try {
            response = userService.getUserById("1");
        } catch (UserNotFoundException e) {
            log.error("Unexpected Mocking Error");
        }
        assertEquals("1", response.getId(), "Found Ana");
        assertEquals("Ana", response.getFirstName(), "Found Ana's Name");
        assertEquals("Mattuzzi", response.getSurname(), "Found Ana's Surname");
        assertEquals(birthday, response.getBirthday(), "Found Ana's Birthday");

    }

    @Test
    public void getSpecificUserFailed() {
        try {
            userService.getUserById("1");
            fail("Expected exception to be thrown");
        } catch (UserNotFoundException e) {
            log.error("User with id 1 is not present");
        }
    }

    @Test
    public void getUsers() {

        LocalDate creationDate = LocalDate.now();

        List<User> users = List.of(User.builder()
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(LocalDate.of(1974, 7, 28))
                .creationDate(creationDate)
                .build(),
                User.builder()
                        .firstName("Miloš")
                        .surname("Mirković")
                        .birthday(LocalDate.of(1977, 10, 23))
                        .creationDate(creationDate)
                        .build(),
                User.builder()
                        .firstName("Hannah")
                        .surname("Standmann")
                        .birthday(LocalDate.of(2005, 11, 05))
                        .creationDate(creationDate)
                        .build());
        Mockito.when(
                userRepository.findAll(
                        Sort.by("birthday")
                                .descending()))
                .thenReturn(users);
        List<UserDto> response = new ArrayList<>(userService.getUsers("", SortOrder.DESC));
        assertEquals(3, response.size());
        assertEquals("Ana", response.get(0).getFirstName());
        assertEquals("Miloš", response.get(1).getFirstName());
        assertEquals("Hannah", response.get(2).getFirstName());
    }

    @Test
    public void createUser() {
        LocalDate birthday = LocalDate.of(1974, 7, 28);
        User user = User.builder()
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(birthday)
                .creationDate(LocalDate.now())
                .build();
        User createdUser = User.builder()
                .id("1")
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(birthday)
                .creationDate(LocalDate.now())
                .build();

        UserDto createUserDto = UserDto.builder()
                .birthday(birthday)
                .firstName("Ana")
                .surname("Mattuzzi")
                .build();

        Mockito.when(
                userRepository.save(user))
                .thenReturn(createdUser);

        UserDto response = userService.createUser(createUserDto);

        assertEquals("1", response.getId(), "Found Ana");
        assertEquals("Ana", response.getFirstName(), "Found Ana's Name");
        assertEquals("Mattuzzi", response.getSurname(), "Found Ana's Surname");
        assertEquals(birthday, response.getBirthday(), "Found Ana's Birthday");

    }

}
