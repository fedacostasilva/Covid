package br.fiap.application;

import static br.fiap.infrastructure.PacienteServiceException.OPERACACAO_INCLUSAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_ATUALIZACAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_BUSCA_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_EXCLUSAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.Paciente_NAO_LOCALIZADO_ERROR;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fiap.domain.entity.Paciente;
import br.fiap.domain.entity.PacienteResponse;
import br.fiap.domain.service.PacienteService;
import br.fiap.infrastructure.PacienteServiceException;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/paciente")
@Slf4j
public class PacienteApplication {

	private final PacienteService servico;

	@Autowired
	public PacienteApplication(PacienteService servico) {
		this.servico = servico;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public PacienteResponse pesquisar(@RequestParam(required = false) String nome) {
		log.info("Processando a requisição de pesquisa");
		try {
			PacienteResponse resposta = new PacienteResponse();
			
			List<Paciente> Pacientes = servico.pesquisar(nome);
			resposta.setPacientes(Pacientes);
			
			return resposta;
		} catch (PacienteServiceException e) {
			log.error("Error processing search request", e);
			throw exceptionHandler(e);
		}
	}

	private ResponseStatusException exceptionHandler(PacienteServiceException e) {
		if (e.getOperation() == null || e.getOperation().isEmpty()) {
			return new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		if (OPERACAO_BUSCA_ERROR.equals(e.getOperation()) || OPERACACAO_INCLUSAO_ERROR.equals(e.getOperation())
				|| OPERACAO_BUSCA_ERROR.equals(e.getOperation()) || OPERACAO_ATUALIZACAO_ERROR.equals(e.getOperation())
				|| OPERACAO_EXCLUSAO_ERROR.equals(e.getOperation())) {
			return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (Paciente_NAO_LOCALIZADO_ERROR.equals(e.getOperation())) {
			return new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return new ResponseStatusException(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Void> incluir(@RequestBody Paciente Paciente) throws PacienteServiceException {
		log.info("Processando resquest de inclusão");
		try {
			Paciente result = servico.incluir(Paciente);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
					.toUri();
			return ResponseEntity.created(location).build();
		} catch (PacienteServiceException e) {
			log.error("Erro ao processar o request de inclusão", e);
			throw exceptionHandler(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Paciente localizarPorChave(@PathVariable Long id) throws PacienteServiceException {
		log.info("Processando request de localização por Chave");
		try {
			return servico.localizarPorChave(id);
		} catch (PacienteServiceException e) {
			log.error("Erro ao processar request de localizar Paciente por chave", e);
			throw exceptionHandler(e);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Paciente atualizar(@PathVariable Long id, @RequestBody Paciente product) throws PacienteServiceException {
		log.info("Processando resquest de atualização");
		try {
			return servico.atualizar(id, product);
		} catch (PacienteServiceException e) {
			log.error("Erro ao processar request de atualizacao", e);
			throw exceptionHandler(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void delete(@PathVariable Long id) throws PacienteServiceException {
		log.info("Processando resquest de exclusão");
		try {
			servico.excluir(id);
		} catch (PacienteServiceException e) {
			log.error("Erro ao processar request de exclusão", e);
			throw exceptionHandler(e);
		}
	}

}
