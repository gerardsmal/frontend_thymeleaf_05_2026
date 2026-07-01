package com.betacom.fe.dto.input;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificatoReq {
	private Boolean tipo;    // false normale true agonisctico
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataCertificato;
	
	private Integer socioId;

}
