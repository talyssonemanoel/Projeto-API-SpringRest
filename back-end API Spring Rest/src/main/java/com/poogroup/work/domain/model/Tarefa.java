package com.poogroup.work.domain.model;

import java.time.OffsetDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.poogroup.work.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Tarefa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	@Embedded
	private Conteudo conteudo;
	
	
	@Enumerated(EnumType.STRING)
	private StatusTarefa status;
	
	private OffsetDateTime dataPedido;
	
	private OffsetDateTime dataFinalizacao;


	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Tarefa não pode ser finalizada");
		}
		
		setStatus(StatusTarefa.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}
	
	public boolean podeSerFinalizada() {
		return StatusTarefa.PENDENTE.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
}
