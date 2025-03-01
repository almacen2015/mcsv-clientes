package backend.mcsvclientes.controllers;

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
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return new ResponseEntity<>(clienteService.listar(), HttpStatus.OK);
    }

}
