package backend.mcsvclientes.services;

import backend.mcsvclientes.models.dtos.ClienteRequestDTO;
import backend.mcsvclientes.models.dtos.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO registrar(ClienteRequestDTO cliente);

    List<ClienteResponseDTO> listar();

}
