package com.alamara.journalApp.service;

import com.alamara.journalApp.Entity.User;
import com.alamara.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvSources;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UsersArgumentsProvider.class)
    public void testFindByUserName(User user)
    {
       assertTrue(userService.saveNewUser(user));

    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
