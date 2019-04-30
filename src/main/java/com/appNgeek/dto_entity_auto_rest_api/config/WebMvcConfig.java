package com.appNgeek.dto_entity_auto_rest_api.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.appNgeek.dto_entity_auto_rest_api.convertor.PageToPageConverter;
import com.appNgeek.dto_entity_auto_rest_api.convertor.handler.RequestDTOToDomainEntHandlerMethodArgumentResolver;
import com.appNgeek.dto_entity_auto_rest_api.convertor.handler.ResponseEntityToDTOHandlerMethodArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Thanneer
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final ApplicationContext applicationContext;

	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@Autowired
	public WebMvcConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
	    registry.addConverter(new PageToPageConverter((ConversionService) registry));
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().applicationContext(this.applicationContext).build();
		argumentResolvers.add(new RequestDTOToDomainEntHandlerMethodArgumentResolver(objectMapper, applicationContext));
	}

	@PostConstruct
	private void overwriteDefaultRequestResponseBodyMethodProcessor() {
		List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
		handlers = decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);
	}

	private List<HandlerMethodReturnValueHandler> decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		List<HandlerMethodReturnValueHandler> handlersNewList = new ArrayList<>();

		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (!(handler instanceof RequestResponseBodyMethodProcessor)) {
				handlersNewList.add(handler);
			} else {
				List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
				messageConverters.add(new MappingJackson2HttpMessageConverter());
				ResponseEntityToDTOHandlerMethodArgumentResolver decorator = new ResponseEntityToDTOHandlerMethodArgumentResolver(
						messageConverters, applicationContext);
				handlersNewList.add(decorator);
			}

		}

		return handlersNewList;
	}

}