package com.poogroup.work.domain.service;

import org.springframework.stereotype.Service;

import com.poogroup.work.domain.exception.EntidadeNaoEncontradaException;
import com.poogroup.work.domain.model.Tarefa;
import com.poogroup.work.domain.repository.TarefaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaTarefaService {

	private TarefaRepository tarefaRepository;
	
	public Tarefa buscar(Long taskId) {
		return tarefaRepository.findById(taskId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Tarefa não encontrada"));
	}
	
}
