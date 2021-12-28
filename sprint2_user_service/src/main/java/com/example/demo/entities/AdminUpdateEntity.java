package com.example.demo.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEntity {
	@NotBlank
	private String adminusername;
	@NotBlank
	private String username;
	@NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
}
