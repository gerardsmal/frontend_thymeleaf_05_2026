package com.betacom.fe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.betacom.fe.dto.input.CertificatoReq;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.dto.output.SocioDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller

public class CertificatoController {
	private final WebClient webClient;
	
	@GetMapping("updateCertificato")
	public ModelAndView updateCertificato(@RequestParam  Integer id,   Model model) {
		log.debug("updateCertificato {}", id);
		ModelAndView mav = new ModelAndView("listCertificato");
		
		SocioDTO soc = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("socio/getById")
						.queryParam("id", id)
						.build())
				.retrieve()
				.bodyToMono(SocioDTO.class)
				.block();
		
		CertificatoReq req = null;
		if (soc.getCertificato() == null) 
			req = CertificatoReq.builder()
				.socioId(id)
				.build();
			else req = CertificatoReq.builder()
				.dataCertificato(soc.getCertificato().getDataCertificato())
				.tipo((soc.getCertificato().getTipo().equalsIgnoreCase("agonistico")))
				.socioId(id)
				.build();
		log.debug("request {}", req);
		mav.addObject("certificato",req);
		mav.addObject("titolo", "Certificato medico");
		return mav;
	}
	
	@PostMapping("saveCertificato")
	public String saveCertificato(@ModelAttribute("certificato") CertificatoReq req) {
		log.debug("saveCertificato {}", req);
		
		ResponseEntity<ResponseDTO> response = webClient.put()
				.uri("certificato/update")
				.bodyValue(req)
				.exchangeToMono(resp -> resp.toEntity(ResponseDTO.class) )
				.block();
		
		log.debug("response {}", response.getBody().getMsg());
		return "redirect:/listSocio";
	}
}
