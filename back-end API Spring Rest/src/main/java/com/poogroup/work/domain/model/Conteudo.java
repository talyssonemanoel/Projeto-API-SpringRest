package com.poogroup.work.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Conteudo {

	@NotBlank
	@Column(name = "conteudo_titulo")
	private String titulo;
	
	@NotBlank
	@Column(name = "conteudo_subtitulo")
	private String subtitulo;
	
	@NotBlank
	@Column(name = "conteudo_texto")
	private String texto;
	
}
