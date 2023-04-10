package com.poogroup.work.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConteudoInput {

	@NotBlank
	private String titulo;
	
	@NotBlank
	private String subtitulo;
	
	@NotBlank
	private String texto;
	
}
