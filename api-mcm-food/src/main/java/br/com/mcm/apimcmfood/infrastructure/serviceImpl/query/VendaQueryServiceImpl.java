package br.com.mcm.apimcmfood.infrastructure.serviceImpl.query;

import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.domain.entity.StatusPedido;
import br.com.mcm.apimcmfood.domain.filtros.FiltroVendaDiaria;
import br.com.mcm.apimcmfood.domain.projecoes.VendasDiaria;
import br.com.mcm.apimcmfood.domain.service.VendasQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendasQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendasDiaria> consultarVendasDiarias(FiltroVendaDiaria filtro, String timeOffset) {
        var predicates = new ArrayList<Predicate>();
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendasDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateConvertzDataCriacao = builder.function(
                "convert_tz",
                Date.class,
                root.get("dataCriacao"),
                builder.literal("+00:00"), builder.literal(timeOffset)
        );

        var functionDateDataCriacao = builder.function(
                "date",
                Date.class,
                functionDateConvertzDataCriacao
        );

        var selection = builder.construct(VendasDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        if (filtro.getRestauranteId() != null) {
            predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }

        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoInicio()));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
