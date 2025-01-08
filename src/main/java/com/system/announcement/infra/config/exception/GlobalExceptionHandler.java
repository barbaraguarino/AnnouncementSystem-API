package com.system.announcement.infra.config.exception;

import com.system.announcement.exceptions.*;
import io.micrometer.common.lang.NonNull;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        logger.error("Erro ao processar o JSON: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("JSON com formato invalido. " +
                        "Por favor verifique o corpo da requisição e tento novamente.");
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    private ResponseEntity<Object> AuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException ex){
        logger.error("Erro nas Credenciais de Autenticação: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Object> ConstraintViolationException(ConstraintViolationException ex){
        logger.error("Erro na Validação dos Dados: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na validação dos dados recebidos, " +
                "por favor verifique e tente novamente.");
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    private ResponseEntity<Object> AnnouncementNotFoundException(AnnouncementNotFoundException ex){
        logger.error("Erro na busca do anúncio: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(WithoutAuthorizationException.class)
    private ResponseEntity<Object> WithoutAuthorizationException(WithoutAuthorizationException ex){
        logger.error("Erro na tentativa de execução da requisição: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AnnouncementIsClosedException.class)
    private ResponseEntity<Object> AnnouncementIsClosedException(AnnouncementIsClosedException ex){
        logger.error("Erro na tentativa de modificar um anúncio fechado: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AnnouncementIsDeletedException.class)
    private ResponseEntity<Object> AnnouncementIsDeletedException(AnnouncementIsDeletedException ex){
        logger.error("Erro na tentativa de acessar ou modicar um anúncio deletado: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
    }

    @ExceptionHandler(AssessmentAlreadyDoneException.class)
    private ResponseEntity<Object> AssessmentAlreadyDoneException(AssessmentAlreadyDoneException ex){
        logger.error("Erro ao tentar realizar novamente a avaliação: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<Object> CategoryNotFoundException(CategoryNotFoundException ex){
        logger.error("Erro ao buscar categoria: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CategoryIsEmptyException.class)
    private ResponseEntity<Object> CategoryIsEmptyException(CategoryIsEmptyException ex){
        logger.error("Erro ao buscar categoria: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ChatNotFoundException.class)
    private ResponseEntity<Object> ChatNotFoundException(ChatNotFoundException ex){
        logger.error("Erro ao buscar chat: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CityNotFoundException.class)
    private ResponseEntity<Object> CityNotFoundException(CityNotFoundException ex){
        logger.error("Erro ao buscar cidade: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<Object> UserNotFoundException(UserNotFoundException ex){
        logger.error("Erro ao buscar usuário: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}