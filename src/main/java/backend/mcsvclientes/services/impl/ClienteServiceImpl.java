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
    public ClienteResponseDTO registrar(ClienteRequestDTO dto) {
        verificarNombre(dto.nombre());
        verificarApellido(dto.apellido());
        verificarNumeroDocumento(dto.numeroDocumento());
        verificarTipoDocumento(dto.tipoDocumento());
        verificarFechaNacimiento(dto.fechaNacimiento());

        Cliente cliente = clienteMapper.toEntity(dto);

        clienteRepository.save(cliente);

        ClienteResponseDTO response = clienteMapper.toResponseDTO(cliente);

        return response;
    }

    @Override
    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clienteMapper.toListResponseDTO(clientes);
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        verificarId(id);
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteException(ClienteException.CLIENTE_NO_ENCONTRADO));

        return clienteMapper.toResponseDTO(cliente);
    }

    private void verificarLetraNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento.matches(".*[a-zA-Z]+.*")) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_INVALIDO);
        }
    }

    private void verificarId(Long id) {
        if (id == null || id <= 0) {
            throw new ClienteException(ClienteException.ID_INVALIDO);
        }
    }

    private void verificarNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new ClienteException(ClienteException.NOMBRE_INVALIDO);
        }
    }

    private void verificarApellido(String apellido) {
        if (apellido == null || apellido.isEmpty()) {
            throw new ClienteException(ClienteException.APELLIDO_INVALIDO);
        }
    }

    private void verificarNumeroDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.isEmpty()) {
            throw new ClienteException(ClienteException.NUMERO_DOCUMENTO_INVALIDO);
        }
    }

    private void verificarTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            throw new ClienteException(ClienteException.TIPO_DOCUMENTO_INVALIDO);
        }

        if (!tipoDocumento.equals(TipoDocumento.PASAPORTE.name()) && !tipoDocumento.equals(TipoDocumento.DNI.name())) {
            throw new ClienteException(ClienteException.TIPO_DOCUMENTO_INVALIDO);
        }

        verificarLetraNumeroDocumento(tipoDocumento);
    }

    private void verificarFechaNacimiento(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_INVALIDA);
        }

        LocalDate fechaNacimientoLocalDate = convertirFechaNacimiento(fechaNacimiento);
        if (fechaNacimientoLocalDate.isAfter(LocalDate.now())) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_INVALIDA);
        }
    }

    private LocalDate convertirFechaNacimiento(String fechaNacimiento) {
        try {
            return LocalDate.parse(fechaNacimiento);
        } catch (Exception e) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_INVALIDA);
        }
    }

    private String convertirFechaNacimiento(LocalDate fechaNacimiento) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return fechaNacimiento.format(formatter);
        } catch (Exception e) {
            throw new ClienteException(ClienteException.FECHA_NACIMIENTO_INVALIDA);
        }
    }

}
