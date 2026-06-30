package com.betacom.fe.dto.output;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CertificatoDTO {
	private Integer id;
	private String tipo;    // false normale true agonisctico
	private LocalDate dataCertificato;

}
