package br.fiap.domain.service;

import java.io.Serializable;
import java.util.List;

import br.fiap.domain.entity.Paciente;
import br.fiap.infrastructure.PacienteServiceException;

public interface PacienteService extends Serializable {

	List<Paciente> pesquisar(String nome) throws PacienteServiceException;
	
	Paciente incluir(Paciente Paciente) throws PacienteServiceException;
	
	Paciente localizarPorChave(Long id) throws PacienteServiceException;
	
	Paciente atualizar(Long id, Paciente Paciente) throws PacienteServiceException;
	
	void excluir(Long id) throws PacienteServiceException;
	
}
