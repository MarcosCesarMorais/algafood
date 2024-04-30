package br.com.mcm.apimcmfood.paginacao;

import java.util.List;
import java.util.function.Function;

public record Paginacao<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items
) {

    public <R> Paginacao<R> map(final Function<T, R> mapper) {
        final List<R> aNewList = this.items.stream()
                .map(mapper)
                .toList();

        return new Paginacao<>(currentPage(), perPage(), total(), aNewList);
    }
}
