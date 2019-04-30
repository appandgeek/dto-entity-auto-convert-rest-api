//package com.appNgeek.dto_entity_auto_rest_api.convertor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.stereotype.Component;
//
//import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
//import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;
//
//@Component
//public class ArticleToArticleDTOConvertorPage implements Converter<Page<Article>, Page<ArticleDTO>> {
//
//	
//	public ArticleDTO convert(Article article) {
//		ArticleDTO articleDTO = new ArticleDTO(article.getTitle(), article.getBody(), article.getUser().getId());
//		articleDTO.setId(article.getId());
//		return articleDTO;
//	}
//
//	@Override
//	public Page<ArticleDTO> convert(Page<Article> source) {
//		List<ArticleDTO> articleDTOList = new ArrayList<>();
//		for (Article article : source) {
//			articleDTOList.add(convert(article));
//		}
//		return new PageImpl<ArticleDTO>(articleDTOList,source.getPageable(),source.getTotalElements());
//	}
//
//}