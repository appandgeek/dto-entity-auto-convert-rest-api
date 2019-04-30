package com.appNgeek.dto_entity_auto_rest_api.convertor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

public class PageToPageConverter implements ConditionalGenericConverter {

	private final ConversionService conversionService;

	public PageToPageConverter(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(Page.class, Page.class));
	}

	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return canConvertElements(sourceType.getElementTypeDescriptor(), targetType.getElementTypeDescriptor(), this.conversionService);
	}
	public static boolean canConvertElements(@Nullable TypeDescriptor sourceElementType, @Nullable TypeDescriptor targetElementType,
			ConversionService conversionService) {

		if (targetElementType == null) {
			// yes
			return true;
		}
		if (sourceElementType == null) {
			// maybe
			return true;
		}
		if (conversionService.canConvert(sourceElementType, targetElementType)) {
			// yes
			return true;
		}
		if (ClassUtils.isAssignable(sourceElementType.getType(), targetElementType.getType())) {
			// maybe
			return true;
		}
		// no
		return false;
	}

	@Override
	@Nullable
	public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		if (source == null) {
			return null;
		}
		Page<?> sourcePage = (Page<?>) source;

		if (sourcePage.isEmpty()) {
			return source;
		}

		TypeDescriptor elementDesc = TypeDescriptor.valueOf(targetType.getResolvableType().getGenerics()[0].getRawClass());
		if (elementDesc == null) {
			return source;
		}

		// At this point, we need a collection copy in any case, even if just for finding out about element copies...
		Collection<Object> targetCollection = CollectionFactory.createCollection(List.class, elementDesc.getType(), sourcePage.getSize());

		for (Object sourceElement : sourcePage) {
			Object targetElement = this.conversionService.convert(sourceElement, sourceType.elementTypeDescriptor(sourceElement),
					elementDesc);
			targetCollection.add(targetElement);
		}

		return new PageImpl<>(new ArrayList<>(targetCollection), sourcePage.getPageable(), sourcePage.getTotalElements());
	}

}