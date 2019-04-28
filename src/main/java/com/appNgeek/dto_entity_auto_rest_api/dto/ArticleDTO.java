package com.appNgeek.dto_entity_auto_rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO extends BaseEntityDTO{

	private String title;

	private String body;

	private Long userId;
}
