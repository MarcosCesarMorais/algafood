package br.com.mcm.apimcmfood.infrastructure.repository;

import br.com.mcm.apimcmfood.domain.model.Restaurante;
import br.com.mcm.apimcmfood.domain.repository.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RestauranteRepositoryImpl implements RestauranteRepository {
    @PersistenceContext
    private EntityManager manager;
    @Transactional
    @Override
    public List<Restaurante> listar() {
        return manager.createQuery("from Restaurante", Restaurante.class)
                .getResultList();
    }
    @Transactional
    @Override
    public Restaurante buscar(Long id) {
        return manager.find(Restaurante.class, id);
    }
    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }
    @Transactional
    @Override
    public void remover(Restaurante restaurante) {
        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }
}
