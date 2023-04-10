package com.poogroup.work.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poogroup.work.api.assembler.TarefaAssembler;
import com.poogroup.work.api.model.TarefaModel;
import com.poogroup.work.api.model.input.TarefaInput;
import com.poogroup.work.domain.model.Tarefa;
import com.poogroup.work.domain.repository.TarefaRepository;
import com.poogroup.work.domain.service.FinalizacaoTarefaService;
import com.poogroup.work.domain.service.SolicitacaoTarefaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("/tasks")
public class TarefaController {

	private TarefaRepository tarefaRepository;
	private SolicitacaoTarefaService solicitacaoTarefaService;
	private FinalizacaoTarefaService finalizacaoTarefaService;
	private TarefaAssembler tarefaAssembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TarefaModel solicitar(@Valid @RequestBody TarefaInput tarefaInput) {
		Tarefa novaEntrega = tarefaAssembler.toEntity(tarefaInput);
		Tarefa entregaSolicitada = solicitacaoTarefaService.solicitar(novaEntrega);
		
		return tarefaAssembler.toModel(entregaSolicitada);
	}
	
	@PutMapping("/{taskId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long taskId) {
		finalizacaoTarefaService.finalizar(taskId);
	}
	
	@GetMapping
	public List<TarefaModel> listar() {
		return tarefaAssembler.toCollectionModel(tarefaRepository.findAll());
	}
	
	@GetMapping("/{taskId}")
	public ResponseEntity<TarefaModel> buscar(@PathVariable Long taskId) {
		return tarefaRepository.findById(taskId)
				.map(task -> ResponseEntity.ok(tarefaAssembler.toModel(task)))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
