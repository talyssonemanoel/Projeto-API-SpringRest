package com.poogroup.work.api.model;

import java.time.OffsetDateTime;

import com.poogroup.work.domain.model.StatusTarefa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaModel {

	private Long id;
	private ClienteResumoModel cliente;
	private ConteudoModel conteudo;
	private StatusTarefa status;
	private OffsetDateTime dataPedido;
	private OffsetDateTime dataFinalizacao;
	
}
