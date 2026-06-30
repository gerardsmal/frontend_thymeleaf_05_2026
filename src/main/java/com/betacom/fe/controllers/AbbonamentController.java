package com.betacom.fe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.betacom.fe.dto.output.SocioDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AbbonamentController {

	private final WebClient webClient;
	
	
	@GetMapping("listAbbonamento")
	public ModelAndView listAbbonamento(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView("listAbbonamento");
		
		SocioDTO soc = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("socio/getById")
						.queryParam("id", id)
						.build())
				.retrieve()
				.bodyToMono(SocioDTO.class)
				.block();
		
		mav.addObject("socio", soc);
		mav.addObject("titolo", "Elenco abbonamento per " + soc.getNome() + " " + soc.getCognome());	
		
		return mav;
	}
	
}
