package backend.mcsvclientes.exceptions.advice;

import backend.mcsvclientes.exceptions.ClienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Set<String> ERRORES_VALIDACION = Set.of(
            ClienteException.ID_INVALIDO,
            ClienteException.APELLIDO_INVALIDO,
            ClienteException.NOMBRE_INVALIDO,
            ClienteException.FECHA_NACIMIENTO_INVALIDA,
            ClienteException.NUMERO_DOCUMENTO_INVALIDO,
            ClienteException.TIPO_DOCUMENTO_INVALIDO,
            ClienteException.CLIENTE_NO_ENCONTRADO
    );

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<String> handleClienteException(ClienteException e) {
        log.error(e.getMessage(), e);

        HttpStatus status = ERRORES_VALIDACION.contains(e.getMessage()) ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(e.getMessage());
    }
}

