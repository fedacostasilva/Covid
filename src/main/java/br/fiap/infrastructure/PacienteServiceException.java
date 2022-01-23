package br.fiap.infrastructure;

import lombok.Data;

@Data
public class PacienteServiceException extends Exception{
	
	/**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final String OPERACAO_PROCURA_ERROR = "PROCURA_ERROR";
    public static final String PARAMETRO_INVALIDO_ERROR = "PARAMETRO_INVALIDO_ERROR";
    public static final String Paciente_NAO_LOCALIZADO_ERROR = "Paciente_NAO_LOCALIZADO_ERROR";
    public static final String OPERACACAO_INCLUSAO_ERROR = "INCLUSAO_ERROR";
    public static final String OPERACAO_BUSCA_ERROR = "BUSCA_ERROR";
    public static final String OPERACAO_ATUALIZACAO_ERROR = "ATUALIZACAO_ERROR";
    public static final String OPERACAO_EXCLUSAO_ERROR = "EXCLUSAO_ERROR";
    private final String operation;

    public PacienteServiceException(String operation, Throwable cause) {
        super(cause);
        this.operation = operation;
    }

}
