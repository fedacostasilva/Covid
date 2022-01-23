package br.fiap.domain.service;

import static br.fiap.infrastructure.PacienteServiceException.OPERACACAO_INCLUSAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_ATUALIZACAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_BUSCA_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_EXCLUSAO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.OPERACAO_PROCURA_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.PARAMETRO_INVALIDO_ERROR;
import static br.fiap.infrastructure.PacienteServiceException.Paciente_NAO_LOCALIZADO_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.fiap.application.PacienteApplication;
import br.fiap.domain.entity.Paciente;
import br.fiap.domain.repository.PacienteRepository;
import br.fiap.infrastructure.PacienteServiceException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class PacienteServiceImpl implements PacienteService {

	/**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(PacienteApplication.class);
	
	private final PacienteRepository repositorio;
			
	@Autowired
    public PacienteServiceImpl(PacienteRepository repositorio) {
        this.repositorio = repositorio;
    }
	
	@Override
    public List<Paciente> pesquisar(String nome) throws PacienteServiceException {
        log.info("Pesquisando Pacientes com a descrição={}", nome);
        try {
            List<Paciente> resultado = new ArrayList<>();

            if (nome == null || nome.isEmpty()) {
                log.debug("Sem nome especificado, listando todos Pacientes");
                resultado.addAll(repositorio.findAll());
            } 

            return resultado;
        } catch (Exception e) {
            log.error("Erro ao procurar paciente", e);
            throw new PacienteServiceException(OPERACAO_PROCURA_ERROR, e);
        }
    }

    @Override
    public Paciente incluir(Paciente Paciente) throws PacienteServiceException {
        log.info("Incluindo Paciente ({})", Paciente);
        try {
            if (Paciente == null) {
                log.error("Paciente inválido");
                throw new PacienteServiceException(PARAMETRO_INVALIDO_ERROR, null);
            }
            Paciente resultado = repositorio.save(Paciente);
            return resultado;
        } catch (Exception e) {
            log.error("Erro ao criar Paciente", e);
            throw new PacienteServiceException(OPERACACAO_INCLUSAO_ERROR, e);
        }
    }

    @Override
    public Paciente localizarPorChave(Long id) throws PacienteServiceException {
        log.info("Recuperando Paciente por id={}", id);
        try {
            if (id == null) {
                log.error("id inválido");
                throw new PacienteServiceException(PARAMETRO_INVALIDO_ERROR, null);
            }
            Paciente resultado = repositorio.findById(id).get();
            return resultado;
        } catch (Exception e) {
            log.error("Erro ao recuperar id", e);
            throw new PacienteServiceException(OPERACAO_BUSCA_ERROR, e);
        }
    }

    @Override
    public Paciente atualizar(Long id, Paciente Paciente) throws PacienteServiceException {
        log.info("Atualizando Paciente ({}) para o id={}", Paciente, id);
        try {
            if (id == null || Paciente == null) {
                log.error("Id invalido ou Paciente");
                throw new PacienteServiceException(PARAMETRO_INVALIDO_ERROR, null);
            }
            if (!repositorio.existsById(id)) {
                log.debug("Paciente não localizado para o id={}", id);
                throw new PacienteServiceException(Paciente_NAO_LOCALIZADO_ERROR, null);
            }
            Paciente resultado = repositorio.save(Paciente);
            return resultado;
        } catch (Exception e) {
            log.error("Error ao atualizar paciente", e);
            throw new PacienteServiceException(OPERACAO_ATUALIZACAO_ERROR, e);
        }
    }

    @Override
    public void excluir(Long id) throws PacienteServiceException {
        log.info("Excluindo Paciente para o id={}", id);
        try {
            if (id == null) {
                log.error("Id invalido ou Paciente");
                throw new PacienteServiceException(PARAMETRO_INVALIDO_ERROR, null);
            }
            if (!repositorio.existsById(id)) {
                log.debug("Paciente não localizado para o id={}", id);
                throw new PacienteServiceException(Paciente_NAO_LOCALIZADO_ERROR, null);
            }
            repositorio.deleteById(id);
        } catch (Exception e) {
            log.error("Erro ao excluir o paciente", e);
            throw new PacienteServiceException(OPERACAO_EXCLUSAO_ERROR, e);
        }
    }
}