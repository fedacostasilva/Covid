package br.fiap.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fiap.domain.entity.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	List<Paciente> findByNomeAllIgnoreCase(String nome);
}
