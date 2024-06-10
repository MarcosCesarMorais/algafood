package br.com.mcm.apimcmfood.infrastructure.repository;

import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    Page<Cozinha> findByNomeContaining(String nome, Pageable pageable);
}
