package backend.mcsvclientes.controllers;

import backend.mcsvclientes.models.dtos.ClienteRequestDTO;
import backend.mcsvclientes.models.dtos.ClienteResponseDTO;
import backend.mcsvclientes.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Listar Clientes", description = "Lista todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listAll() {
        return new ResponseEntity<>(clienteService.listAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Registrar un cliente", description = "Registra un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente registrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> add(@RequestBody ClienteRequestDTO cliente) {
        return new ResponseEntity<>(clienteService.add(cliente), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Buscar un cliente por ID", description = "Busca un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Buscar cliente por número de documento", description = "Buscar cliente por número de documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @GetMapping("/document/{documentNumber}/{documentType}")
    public ResponseEntity<ClienteResponseDTO> getByDocumentNumber(@PathVariable String documentNumber, @PathVariable String documentType) {
        return new ResponseEntity<>(clienteService.getByDocumentNumber(documentNumber, documentType), HttpStatus.OK);
    }

}
