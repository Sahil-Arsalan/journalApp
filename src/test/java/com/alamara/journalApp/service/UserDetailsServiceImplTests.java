package com.alamara.journalApp.service;

import com.alamara.journalApp.Entity.User;
import com.alamara.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUserNameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Sahil").password("asdbad").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("Sahil");
        Assertions.assertNotNull(user);
    }
}
