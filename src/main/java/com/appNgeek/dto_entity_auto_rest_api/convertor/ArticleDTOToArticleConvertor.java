package com.appNgeek.dto_entity_auto_rest_api.convertor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.domain.User;
import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;
import com.appNgeek.dto_entity_auto_rest_api.repo.UserRepository;

@Component
public class ArticleDTOToArticleConvertor implements Converter<ArticleDTO, Article> {

	private final ApplicationContext applicationContext;

	@Autowired
	public ArticleDTOToArticleConvertor(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Article convert(ArticleDTO articleDTO) {
		UserRepository userRepository = applicationContext.getBean(UserRepository.class);
		Optional<User> user = userRepository.findById(articleDTO.getUserId());
		Article article = new Article(articleDTO.getTitle(), articleDTO.getBody(), user.get(), null);
		return article;
	}

}