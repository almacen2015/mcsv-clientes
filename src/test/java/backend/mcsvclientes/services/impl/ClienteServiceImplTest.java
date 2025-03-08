package backend.mcsvclientes.services.impl;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteServiceImpl service;

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