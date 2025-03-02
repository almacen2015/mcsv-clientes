package backend.mcsvclientes.exceptions.advice;

import backend.mcsvclientes.exceptions.ClienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<?> exceptionHandler(ClienteException e) {
        if (e.getMessage().equals(ClienteException.ID_INVALIDO) ||
                e.getMessage().equals(ClienteException.APELLIDO_INVALIDO) ||
                e.getMessage().equals(ClienteException.NOMBRE_INVALIDO) ||
                e.getMessage().equals(ClienteException.FECHA_NACIMIENTO_INVALIDA) ||
                e.getMessage().equals(ClienteException.NUMERO_DOCUMENTO_INVALIDO) ||
                e.getMessage().equals(ClienteException.TIPO_DOCUMENTO_INVALIDO)) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

