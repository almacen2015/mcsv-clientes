package backend.mcsvclientes.services.impl;

import backend.mcsvclientes.exceptions.ClienteException;
import backend.mcsvclientes.models.dtos.ClienteRequestDTO;
import backend.mcsvclientes.models.dtos.ClienteResponseDTO;
import backend.mcsvclientes.models.entities.Cliente;
import backend.mcsvclientes.models.entities.TipoDocumento;
import backend.mcsvclientes.repositories.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl service;

    @Test
    void testAdd_whenApellidoIsBlank_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO("Victor", "    ", "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }

    @Test
    void testAdd_whenApellidoIsNull_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO("Victor", null, "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }

    @Test
    void testAdd_whenApellidoIsEmpty_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO("Victor", "", "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }

    @Test
    void testAdd_whenNameIsBlank_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO("     ", "Orbegozo", "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }


    @Test
    void testAdd_whenNameIsNull_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO(null, "Orbegozo", "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }

    @Test
    void testAdd_whenNameIsEmpty_returnError() {
        ClienteRequestDTO dto = new ClienteRequestDTO("", "Orbegozo", "12345678", "DNI", "1994-05-04");

        assertThrows(ClienteException.class, () -> service.add(dto));
    }

    @Test
    void testAdd_whenDataValid_ReturnClient() {
        Cliente cliente1 = Cliente.builder()
                .id(1L)
                .nombre("Victor")
                .apellido("Orbegozo")
                .numeroDocumento("12345678")
                .tipoDocumento(TipoDocumento.DNI)
                .fechaNacimiento(LocalDate.of(1994, 5, 4))
                .build();

        ClienteRequestDTO dto = new ClienteRequestDTO("Victor", "Orbegozo", "12345678", "DNI", "1994-05-04");

        when(repository.save(any(Cliente.class))).thenReturn(cliente1);

        ClienteResponseDTO response = service.add(dto);

        assertThat(response).isNotNull();
        assertEquals(1, response.id());
        assertEquals("Victor", response.nombre());
        assertEquals("Orbegozo", response.apellido());
        assertEquals("12345678", response.numeroDocumento());
        assertEquals("DNI", response.tipoDocumento());
    }

    @Test
    void testListAll_whenDataEmpty_returnEmpty() {
        when(repository.findAll()).thenReturn(List.of());

        List<ClienteResponseDTO> clientes = service.listAll();

        assertThat(clientes).isEmpty();
    }

    @Test
    void testListAll_returnClients() {
        Cliente cliente1 = Cliente.builder()
                .nombre("Victor")
                .apellido("Orbegozo")
                .numeroDocumento("12345678")
                .tipoDocumento(TipoDocumento.DNI)
                .fechaNacimiento(LocalDate.of(1994, 5, 4))
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombre("Maria")
                .apellido("Martinez")
                .numeroDocumento("34567218")
                .tipoDocumento(TipoDocumento.DNI)
                .fechaNacimiento(LocalDate.of(1994, 5, 4))
                .build();

        when(repository.findAll()).thenReturn(List.of(cliente1, cliente2));

        List<ClienteResponseDTO> clientes = service.listAll();

        assertEquals(2, clientes.size());
        assertThat(clientes).hasSize(2);
    }
}