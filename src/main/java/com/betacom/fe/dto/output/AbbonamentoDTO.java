package com.betacom.fe.dto.output;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbbonamentoDTO {
	private Integer id;
	private LocalDate dataIscrizione;
	private Integer durataValidita;
	private List<AttivitaDTO> attivita;

}
