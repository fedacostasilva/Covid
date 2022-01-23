package br.fiap.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Paciente")
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
	private String nome;
    
	private String estado;
	
	private String idade;
	
}
