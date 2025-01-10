package com.system.announcement.services;

import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.dtos.authentication.AuthenticationDTO;
import com.system.announcement.dtos.authentication.LoginDTO;
import com.system.announcement.dtos.user.UserDTO;
import com.system.announcement.exceptions.UserNotFoundException;
import com.system.announcement.infra.token.TokenService;
import com.system.announcement.models.User;
import com.system.announcement.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class Login{

        @Test
        @DisplayName("Deve autenticar usuário e retornar token")
        void shouldAuthenticateUserAndReturnToken() {
            String email = "test@example.com";
            String password = "password123";
            String token = "generated-jwt-token";
            LoginDTO loginDTO = new LoginDTO(email, password);
            User user = new User(email, "Test User", UserType.EMPLOYEE, UserRole.USER);

            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(user);

            when(authenticationManager.authenticate(usernamePassword)).thenReturn(authentication);
            when(tokenService.generateToken(user)).thenReturn(token);

            AuthenticationDTO result = userService.login(loginDTO);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(token, result.token());
            Assertions.assertEquals(user.getEmail(), result.email());
            verify(authenticationManager).authenticate(usernamePassword);
            verify(tokenService).generateToken(user);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar autenticar com credenciais inválidas")
        void shouldThrowExceptionWhenCredentialsAreInvalid() {
            String email = "test@example.com";
            String password = "wrong-password";
            LoginDTO loginDTO = new LoginDTO(email, password);

            UsernamePasswordAuthenticationToken usernamePassword =
                    new UsernamePasswordAuthenticationToken(email, password);

            when(authenticationManager.authenticate(usernamePassword))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            Assertions.assertThrows(BadCredentialsException.class, () ->
                userService.login(loginDTO)
            );

            verify(authenticationManager).authenticate(usernamePassword);
            verifyNoInteractions(tokenService);
        }


    }

    @Nested
    class GetUserByEmail {

        @Test
        @DisplayName("Deve retornar usuário pelo email")
        void shouldReturnUserByEmail() {
            String email = "test@example.com";
            User user = new User(email, "Test User", UserType.EMPLOYEE, UserRole.USER);

            when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

            User result = userService.getUserByEmail(email);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(user.getEmail(), result.getEmail());
            verify(userRepository).getUserByEmail(email);
        }

        @Test
        @DisplayName("Deve lançar exceção ao buscar usuário inexistente pelo email")
        void shouldThrowExceptionWhenUserNotFoundByEmail() {
            String email = "nonexistent@example.com";

            when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

            Assertions.assertThrows(UserNotFoundException.class, () ->
                    userService.getUserByEmail(email)
            );

            verify(userRepository).getUserByEmail(email);
        }
    }

    @Nested
    class Save{

        @Test
        @DisplayName("Deve salvar usuário no repositório")
        void shouldSaveUser() {
            User user = new User("test@example.com", "Test User", UserType.EMPLOYEE, UserRole.USER);

            userService.save(user);

            verify(userRepository).save(user);
        }
    }

    @Nested
    class GetUser {

        @Test
        @DisplayName("Deve retornar UserDTO quando usuário encontrado")
        void shouldReturnUserDTOWhenUserFound() {
            String email = "test@example.com";
            User user = new User(email, "Test User", UserType.EMPLOYEE, UserRole.USER);
            UserDTO expectedUserDTO = new UserDTO(user);

            when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

            UserDTO result = userService.getUser(email);

            Assertions.assertNotNull(result);
            Assertions.assertEquals(expectedUserDTO.email(), result.email());
            verify(userRepository).getUserByEmail(email);
        }
    }


}