package com.appNgeek.dto_entity_auto_rest_api.convertor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.appNgeek.dto_entity_auto_rest_api.dto.BaseEntityDTO;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBodyDTO {

	Class<? extends BaseEntityDTO> value();
}