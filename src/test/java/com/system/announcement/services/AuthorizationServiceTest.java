package com.system.announcement.services;

import com.system.announcement.auxiliary.enums.UserRole;
import com.system.announcement.auxiliary.enums.UserType;
import com.system.announcement.models.User;
import com.system.announcement.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationService authorizationService;

    @Nested
    class LoadUserByUsername {

        @Test
        @DisplayName("Deve carregar o usuário com sucesso para um email válido")
        void shouldLoadUserByUsernameSuccessfully() {
            // Arrange
            String email = "usuario@exemplo.com";
            User user = new User(email, "Usuário Teste", UserType.EMPLOYEE, UserRole.USER);
            user.setPassword("senha");

            Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

            // Act
            UserDetails result = authorizationService.loadUserByUsername(email);

            // Assert
            Assertions.assertNotNull(result, "O usuário retornado não deve ser nulo");
            Assertions.assertEquals(email, result.getUsername(), "O email do usuário deve corresponder ao fornecido");
            Assertions.assertEquals("senha", result.getPassword(), "A senha do usuário deve corresponder ao fornecido");
            Assertions.assertTrue(result.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")), "O usuário deve ter a autoridade ROLE_USER");

            Mockito.verify(userRepository, Mockito.times(1)).findByEmail(email);
        }

    }
}
