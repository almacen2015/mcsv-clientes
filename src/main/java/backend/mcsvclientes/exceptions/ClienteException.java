package backend.mcsvclientes.exceptions;

public class ClienteException extends RuntimeException {
    public static final String ID_INVALIDO = "ID inválido";
    public static final String NOMBRE_INVALIDO = "El nombre no puede ser nulo o vacio";
    public static final String APELLIDO_INVALIDO = "El apellido no puede ser nulo o vacio";
    public static final String NUMERO_DOCUMENTO_INVALIDO = "El número de documento no puede ser nulo o vacio";
    public static final String TIPO_DOCUMENTO_INVALIDO = "El tipo de documento es inválido";
    public static final String FECHA_NACIMIENTO_INVALIDA = "La fecha de nacimiento es inválida";

    public ClienteException(String message) {
        super(message);
    }
}
