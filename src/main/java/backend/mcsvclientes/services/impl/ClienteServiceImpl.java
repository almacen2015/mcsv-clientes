package backend.mcsvclientes.services.impl;

import backend.mcsvclientes.exceptions.ClienteException;
import backend.mcsvclientes.models.dtos.ClienteRequestDTO;
import backend.mcsvclientes.models.dtos.ClienteResponseDTO;
import backend.mcsvclientes.models.entities.Cliente;
import backend.mcsvclientes.models.entities.TipoDocumento;
import backend.mcsvclientes.models.mappers.ClienteMapper;
import backend.mcsvclientes.repositories.ClienteRepository;
import backend.mcsvclientes.services.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional(rollbackFor = ClienteException.class)
    public ClienteResponseDTO add(ClienteRequestDTO dto) {
        validateNombre(dto.nombre());
        validateApellido(dto.apellido());
        validateNumeroDocumento(dto.numeroDocumento());
        validateTipoDocumento(dto.tipoDocumento());
        validateFechaNacimiento(dto.fechaNacimiento());

        Cliente cliente = clienteMapper.toEntity(dto);

        Cliente clientSaved = clienteRepository.save(cliente);

        ClienteResponseDTO response = clienteMapper.toResponseDTO(clientSaved);

        return response;
    }

    @Override
    public List<ClienteResponseDTO> listAll() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clienteMapper.toListResponseDTO(clientes);
    }

    @Override
    public ClienteResponseDTO finById(Long id) {
        validateId(id);
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteException(ClienteException.CLIENT_NOT_FOUND));

        return clienteMapper.toResponseDTO(cliente);
    }

    private void validateLetraNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento.matches(".*[a-zA-Z]+.*")) {
            throw new ClienteException(ClienteException.INVALID_DOCUMENT_NUMBER);
        }
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new ClienteException(ClienteException.ID_INVALID);
        }
    }

    private void validateNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ClienteException(ClienteException.INVALID_NAME);
        }
    }

    private void validateApellido(String apellido) {
        if (apellido == null || apellido.isBlank()) {
            throw new ClienteException(ClienteException.INVALID_LAST_NAME);
        }
    }

    private void validateNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.isEmpty()) {
            throw new ClienteException(ClienteException.INVALID_DOCUMENT_NUMBER);
        }
        validateLetraNumeroDocumento(numeroDocumento);
    }

    private void validateTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            throw new ClienteException(ClienteException.INVALID_DOCUMENT_TYPE);
        }

        if (!tipoDocumento.equals(TipoDocumento.PASAPORTE.name()) && !tipoDocumento.equals(TipoDocumento.DNI.name())) {
            throw new ClienteException(ClienteException.INVALID_DOCUMENT_TYPE);
        }
    }

    private void validateFechaNacimiento(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            throw new ClienteException(ClienteException.INVALID_BIRTH_DATE);
        }

        LocalDate fechaNacimientoLocalDate = convertToLocalDate(fechaNacimiento);
        if (fechaNacimientoLocalDate.isAfter(LocalDate.now())) {
            throw new ClienteException(ClienteException.INVALID_BIRTH_DATE);
        }
    }

    private LocalDate convertToLocalDate(String fechaNacimiento) {
        try {
            return LocalDate.parse(fechaNacimiento);
        } catch (Exception e) {
            throw new ClienteException(ClienteException.INVALID_BIRTH_DATE);
        }
    }

    private String convertString(LocalDate fechaNacimiento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return fechaNacimiento.format(formatter);
        } catch (Exception e) {
            throw new ClienteException(ClienteException.INVALID_BIRTH_DATE);
        }
    }

}
