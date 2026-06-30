package com.betacom.fe.dto.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString 
@NoArgsConstructor
@AllArgsConstructor
public class RicevutaDTO {

	private String nome;
	private String cognome;
	private Long   totale;
	List<AttivitaDTO> riga;
}
