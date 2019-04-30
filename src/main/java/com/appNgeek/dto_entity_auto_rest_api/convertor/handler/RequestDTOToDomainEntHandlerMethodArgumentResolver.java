package com.appNgeek.dto_entity_auto_rest_api.convertor.handler;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.appNgeek.dto_entity_auto_rest_api.convertor.annotation.RequestBodyDTO;
import com.appNgeek.dto_entity_auto_rest_api.domain.Article;
import com.appNgeek.dto_entity_auto_rest_api.domain.User;
import com.appNgeek.dto_entity_auto_rest_api.dto.ArticleDTO;
import com.appNgeek.dto_entity_auto_rest_api.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class RequestDTOToDomainEntHandlerMethodArgumentResolver extends RequestResponseBodyMethodProcessor {

	private final ApplicationContext applicationContext;

	private final ConversionService conversionService;

	@Autowired
	public RequestDTOToDomainEntHandlerMethodArgumentResolver(ObjectMapper objectMapper, ApplicationContext applicationContext) {
		super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
		this.applicationContext = applicationContext;
		this.conversionService = this.applicationContext.getBean(ConversionService.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.hasParameterAnnotation(RequestBodyDTO.class);
	}

	@Override
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		binder.validate();
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		Object obj = super.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
		Class<?> requestDTOType = getRequestBodyDTOType(methodParameter);
		Class<?> entityType = methodParameter.getParameterType();
		if (conversionService.canConvert(requestDTOType, entityType)) {
			Object entity = conversionService.convert(obj, entityType);
			return entity;
		} else
			return obj;
	}

	@Override
	protected Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType)
			throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {
		for (Annotation ann : parameter.getParameterAnnotations()) {
			RequestBodyDTO requestBodyDTO = AnnotationUtils.getAnnotation(ann, RequestBodyDTO.class);
			if (requestBodyDTO != null) {
				return super.readWithMessageConverters(inputMessage, parameter, requestBodyDTO.value());
			}
		}
		throw new RuntimeException();
	}

	private Class getRequestBodyDTOType(MethodParameter parameter) {
		for (Annotation ann : parameter.getParameterAnnotations()) {
			RequestBodyDTO requestBodyDTO = AnnotationUtils.getAnnotation(ann, RequestBodyDTO.class);
			if (requestBodyDTO != null) {
				return requestBodyDTO.value();
			}
		}
		return null;
	}

}