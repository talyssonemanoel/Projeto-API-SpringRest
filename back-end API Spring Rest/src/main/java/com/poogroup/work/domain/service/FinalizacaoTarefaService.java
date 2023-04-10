package com.poogroup.work.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poogroup.work.domain.model.Tarefa;
import com.poogroup.work.domain.repository.TarefaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoTarefaService {

	private TarefaRepository tarefaRepository;
	private BuscaTarefaService buscaTarefaService;
	
	@Transactional
	public void finalizar(Long taskId) {
		Tarefa tarefa = buscaTarefaService.buscar(taskId);
		
		tarefa.finalizar();
		
		tarefaRepository.save(tarefa);
	}
	
}
