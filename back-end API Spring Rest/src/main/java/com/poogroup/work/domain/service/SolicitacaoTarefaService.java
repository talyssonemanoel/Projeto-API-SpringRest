package com.poogroup.work.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poogroup.work.domain.model.Cliente;
import com.poogroup.work.domain.model.StatusTarefa;
import com.poogroup.work.domain.model.Tarefa;
import com.poogroup.work.domain.repository.TarefaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoTarefaService {

	private CatalogoClienteService catalogoClienteService;
	private TarefaRepository tarefaRepository;
	
	@Transactional
	public Tarefa solicitar(Tarefa tarefa) {
		Cliente cliente = catalogoClienteService.buscar(tarefa.getCliente().getId());
		
		tarefa.setCliente(cliente);
		tarefa.setStatus(StatusTarefa.PENDENTE);
		tarefa.setDataPedido(OffsetDateTime.now());
		
		return tarefaRepository.save(tarefa);
	}
	
}
