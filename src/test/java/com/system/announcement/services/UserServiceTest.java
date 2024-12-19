package com.system.announcement.services;

import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;

import com.system.announcement.dtos.authentication.requestAuthenticationRecordDTO;
import com.system.announcement.dtos.authentication.responseAuthenticationRecordDTO;

import com.system.announcement.infra.token.TokenService;
import com.system.announcement.models.User;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    private requestAuthenticationRecordDTO authenticationRecordDTO;

    @BeforeEach
    void setUp() {
        authenticationRecordDTO = new requestAuthenticationRecordDTO("test@example.com", "password");
    }

    @Nested
    class LoginTest {

        @Test
        @DisplayName("Should login successfully and return token.")
        void shouldLoginSuccessfully() {
            // Data
            User user = new User();
            user.setEmail("test@example.com");
            user.setType(UserType.STUDENT);
            user.setRole(UserRole.USER);

            // Arrange
            Authentication authentication = mock(Authentication.class);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(user);
            when(tokenService.generateToken(user)).thenReturn("token123");

            // Act
            responseAuthenticationRecordDTO result = userService.login(authenticationRecordDTO);

            // Assert
            assertNotNull(result);
            assertEquals(user.getEmail(), result.email());
            assertEquals("token123", result.token());
            verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(tokenService, times(1)).generateToken(user);
        }

        @Test
        @DisplayName("Should throw exception when login fails.")
        void shouldThrowExceptionWhenLoginFails() {
            // Arrange
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new RuntimeException("Invalid credentials"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.login(authenticationRecordDTO));
            verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(tokenService, never()).generateToken(any(User.class));
        }
    }

}