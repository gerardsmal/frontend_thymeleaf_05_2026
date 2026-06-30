package com.betacom.fe.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AbbonamentoReq {
	private Integer id;
	private String dataIscrizione;
	private Integer durataValidita;
	private Integer socioId;
	private Integer attivitaId;
}
