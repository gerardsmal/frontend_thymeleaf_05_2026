package com.betacom.fe.dto.output;

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
public class UtenteDTO {
	private String userName;
	private String pwd;
	private String email;
	private String role;
}
