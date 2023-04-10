package com.poogroup.work.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.poogroup.work.api.model.TarefaModel;
import com.poogroup.work.api.model.input.TarefaInput;
import com.poogroup.work.domain.model.Tarefa;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TarefaAssembler {

	private ModelMapper modelMapper;
	
	public TarefaModel toModel(Tarefa tarefa) {
		return modelMapper.map(tarefa, TarefaModel.class);
	}
	
	public List<TarefaModel> toCollectionModel(List<Tarefa> tarefas) {
		return tarefas.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Tarefa toEntity(TarefaInput tarefaInput) {
		return modelMapper.map(tarefaInput, Tarefa.class);
	}
	
}
