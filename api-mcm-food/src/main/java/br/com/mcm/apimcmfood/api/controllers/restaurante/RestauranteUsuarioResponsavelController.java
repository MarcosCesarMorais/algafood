package br.com.mcm.apimcmfood.api.controllers.restaurante;

import br.com.mcm.apimcmfood.api.model.usuario.UsuarioListResponse;
import br.com.mcm.apimcmfood.api.model.usuario.mapper.UsuarioResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.service.RestauranteService;
import br.com.mcm.apimcmfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private UsuarioResponseMapper usuarioResponseMapper;

    private RestauranteService restauranteService;
    private UsuarioService usuarioService;

    public RestauranteUsuarioResponsavelController(
            final RestauranteService restauranteService,
            final UsuarioService usuarioService
    ) {
        this.restauranteService = Objects.requireNonNull(restauranteService);
        this.usuarioService = Objects.requireNonNull(usuarioService);
    }

    @GetMapping
    public List<UsuarioListResponse> listar(final @PathVariable("restauranteId") Long id) {
        Restaurante restaurante = restauranteService.buscar(id);
        return usuarioResponseMapper.toCollectionModel(restaurante.getResponsaveis());
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(
            final @PathVariable("restauranteId") Long restauranteId,
            final @PathVariable("responsavelId") Long responsavelId
    ) {
        restauranteService.associarUsuarioResponsavel(restauranteId, responsavelId);
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(
            final @PathVariable("restauranteId") Long restauranteId,
            final @PathVariable("responsavelId") Long responsavelId
    ) {
        restauranteService.desassociarUsuarioResponsavel(restauranteId, responsavelId);
    }
}
