package backend.mcsvclientes.repositories;

import backend.mcsvclientes.models.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
