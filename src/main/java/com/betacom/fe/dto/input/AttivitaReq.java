package com.betacom.fe.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttivitaReq {
	private Integer id;
	private String descrizione;
	private Long prezzo;

}
