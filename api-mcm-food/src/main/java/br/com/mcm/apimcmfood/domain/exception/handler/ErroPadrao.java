package br.com.mcm.apimcmfood.domain.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.criteria.CriteriaBuilder;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroPadrao(
        Integer status,
        String type,
        String title,
        String detail
) {
    public ErroPadrao(
            final Integer status,
            final String title
    ){
       this(status, title, null,null);
    }
}
