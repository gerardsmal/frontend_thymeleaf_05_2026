package com.betacom.fe.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CertificatoReq {
	private Boolean tipo;    // false normale true agonisctico
	private String dataCertificato;
	private Integer socioId;

}
