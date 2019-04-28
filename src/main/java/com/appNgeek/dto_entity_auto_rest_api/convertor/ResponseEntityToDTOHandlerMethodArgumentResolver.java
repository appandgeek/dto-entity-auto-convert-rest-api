package com.appNgeek.dto_entity_auto_rest_api.convertor;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.appNgeek.dto_entity_auto_rest_api.convertor.annotation.ResponseBodyDTO;
import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;

public final class ResponseEntityToDTOHandlerMethodArgumentResolver extends RequestResponseBodyMethodProcessor  {

	private final ApplicationContext applicationContext;

	@Autowired
	public ResponseEntityToDTOHandlerMethodArgumentResolver(final List<HttpMessageConverter<?>> messageConverters,
			ApplicationContext applicationContext) {
		super(messageConverters);
		this.applicationContext = applicationContext;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return (returnType.getMethodAnnotation(ResponseBodyDTO.class) != null
				|| super.supportsReturnType(returnType));

	}

	@Override
	public void handleReturnValue(Object returnValue, final MethodParameter returnType, final ModelAndViewContainer mavContainer,
			final NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException {
		if (returnValue instanceof Article) {
			Article article = (Article) returnValue;
			ArticleDTO articleDTO = new ArticleDTO(article.getTitle(), article.getBody(), article.getUser().getId());
			articleDTO.setId(article.getId());
			returnValue = articleDTO;
		}
		
		super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
	}

}