package backend.mcsvclientes.controllers;

import backend.mcsvclientes.models.dtos.ClienteRequestDTO;
import backend.mcsvclientes.models.dtos.ClienteResponseDTO;
import backend.mcsvclientes.models.entities.TipoDocumento;
import backend.mcsvclientes.security.TestSecurityConfig;
import backend.mcsvclientes.services.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
@Import(TestSecurityConfig.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetByDocumentNumber_whenClientFound_returnClient() throws Exception {
        ClienteResponseDTO client = new ClienteResponseDTO(
                1L,
                "Victor",
                "Orbegozo",
                "DNI",
                "1994-05-04",
                "12345678"
        );

        when(service.getByDocumentNumber("12345678", TipoDocumento.DNI.name())).thenReturn(client);
        // Act
        mockMvc.perform(get("/api/clientes/document/{documentNumber}/{documentType}","12345678","DNI")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Victor"));

    }

    @Test
    void testAdd_whenRequestIsValid_returnClient() throws Exception {
        ClienteRequestDTO dto = new ClienteRequestDTO(
                "Victor",
                "Orbegozo",
                "DNI",
                "1994-05-04",
                "12345678"
        );

        ClienteResponseDTO response = new ClienteResponseDTO(
                1L,
                "Victor",
                "Orbegozo",
                "DNI",
                "1994-05-04",
                "12345678"
        );
        String json = objectMapper.writeValueAsString(dto);

        when(service.add(any(ClienteRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Victor"));

        verify(service, times(1)).add(any(ClienteRequestDTO.class));

    }

    @Test
    void testGetById_whenClientFound_returnClient() throws Exception {
        ClienteResponseDTO client = new ClienteResponseDTO(
                1L,
                "Victor",
                "Orbegozo",
                "DNI",
                "1994-05-04",
                "12345678"
        );

        when(service.getById(1L)).thenReturn(client);
        // Act
        mockMvc.perform(get("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Victor"));


        verify(service, times(1)).getById(1L);
    }

    @Test
    void testListAll_whenDataNotFound_returnEmpty() throws Exception {
        when(service.listAll()).thenReturn(List.of());
        // Act
        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(service, times(1)).listAll();
    }

    @Test
    void testListAll() throws Exception {
        ClienteResponseDTO cliente1 = new ClienteResponseDTO(
                1L,
                "Victor",
                "Orbegozo",
                "DNI",
                "1994-05-04",
                "12345678"
        );

        ClienteResponseDTO cliente2 = new ClienteResponseDTO(
                2L,
                "Maria",
                "Martinez",
                "DNI",
                "1994-05-04",
                "11111111"
        );

        when(service.listAll()).thenReturn(List.of(cliente1, cliente2));
        // Act
        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(service, times(1)).listAll();
    }

}