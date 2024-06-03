package br.com.mcm.apimcmfood.infrastructure.repository;

import br.com.mcm.apimcmfood.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
