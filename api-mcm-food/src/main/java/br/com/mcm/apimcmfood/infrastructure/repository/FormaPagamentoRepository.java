package br.com.mcm.apimcmfood.infrastructure.repository;

import br.com.mcm.apimcmfood.domain.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
}
