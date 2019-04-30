package com.appNgeek.dto_entity_auto_rest_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseEntityDTO {

	private String name;

	private String email;

	private String password;
}
