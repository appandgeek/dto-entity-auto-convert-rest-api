package com.appNgeek.dto_entity_auto_rest_api.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;

@Component
public class ArticleToArticleDTOConvertor implements Converter<Article, ArticleDTO> {

	@Override
	public ArticleDTO convert(Article article) {
		ArticleDTO articleDTO = new ArticleDTO(article.getTitle(), article.getBody(), article.getUser().getId());
		articleDTO.setId(article.getId());
		return articleDTO;
	}

}