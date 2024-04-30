package br.com.mcm.apimcmfood.paginacao;

public record ConsultadePesquisa(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
