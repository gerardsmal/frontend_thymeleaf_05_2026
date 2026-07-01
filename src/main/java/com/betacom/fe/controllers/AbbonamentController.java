package com.betacom.fe.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.betacom.fe.dto.input.AbbonamentoReq;
import com.betacom.fe.dto.input.AttivitaReq;
import com.betacom.fe.dto.output.AttivitaDTO;
import com.betacom.fe.dto.output.ResponseDTO;
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
	
	@GetMapping("removeAbbonamentoAttivita")
	public String removeAbbonamentoAttivita(
			@RequestParam Integer abbonamentId,
			@RequestParam Integer attivitaId,
			@RequestParam Integer socioId) {
		log.debug("removeAbbonamentoAttivita {} / {} / {}",abbonamentId, attivitaId,  socioId);
		
		ResponseDTO resp = webClient.delete()
				.uri("abbonamento/deleteAttivita/{abbonamentId}/{attivitaId}",abbonamentId, attivitaId )
				.retrieve()
				.bodyToMono(ResponseDTO.class)
				.block();
		log.debug("response delete : {}", resp.getMsg());
		
		return "redirect:/listAbbonamento?id=" + socioId;
	}
	
	@GetMapping("aggiungiAttivita")
	public ModelAndView aggiungiAttivita(
			@RequestParam Integer abbonamentId,
			@RequestParam Integer socioId){
		log.debug("aggiungiAttivita {} / {}", abbonamentId, socioId);
		ModelAndView mav = new ModelAndView("aggiungiAttivita");
		
		List<AttivitaDTO> att = webClient.get()
				.uri("attivita/list")
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<AttivitaDTO>>() {})
				.block();
		
		log.debug("attivita size {}", att.size());
		mav.addObject("listAttivita", att);
		AbbonamentoReq req = new AbbonamentoReq();
		req.setId(abbonamentId);
		req.setSocioId(socioId);
		mav.addObject("param", req);
		
		return mav;
	}
	
	@PostMapping("saveAbbonamentoAttivita")
	public String saveAbbonamentoAttivita(@ModelAttribute("param")AbbonamentoReq req , RedirectAttributes ra) {
		log.debug("saveAbbonamentoAttivita {}", req);
		
		ResponseEntity<ResponseDTO>  response = webClient.post()
				.uri("abbonamento/addAttivita")
				.bodyValue(req)
				.exchangeToMono(resp -> resp.toEntity(ResponseDTO.class))
				.block();
		log.debug("response : {}", response.getBody().getMsg());
		
		if (!response.getStatusCode().is2xxSuccessful()) {
			ra.addFlashAttribute("errorMsg", response.getBody().getMsg());
			return "redirect:/aggiungiAttivita?abbonamentId=" + req.getId() + "&socioId=" + req.getSocioId();
		}
			
		return "redirect:/listAbbonamento?id=" + req.getSocioId();
		
		
	}
	
	@GetMapping("createAbbonamento")
	public String createAbbonamento(@RequestParam Integer id) {
		log.debug("createAbbonamento {}", id);
		
		AbbonamentoReq req = new AbbonamentoReq();
		req.setSocioId(id);
		req.setDataIscrizione(LocalDate.now());
		
		ResponseEntity<ResponseDTO> response = webClient.post()
				.uri("abbonamento/create")
				.bodyValue(req)
				.exchangeToMono(resp -> resp.toEntity(ResponseDTO.class))
				.block();
		
		log.debug("response:{}", response.getBody().getMsg());
		
		return "redirect:/listAbbonamento?id=" + id;
	}
	
	@GetMapping("removeAbbonamento")
	public String removeAbbonamento(@RequestParam Integer abbonamentoId, @RequestParam Integer socioId) {
		log.debug("removeAbbonamento {} / {}", abbonamentoId, socioId);
		
		ResponseDTO response = webClient.delete()
				.uri("abbonamento/delete/{abbonamentoId}/{socioId}", abbonamentoId, socioId)
				.retrieve()
				.bodyToMono(ResponseDTO.class)
				.block();
		log.debug("response delete {}", response.getMsg());
		
		return "redirect:/listAbbonamento?id=" + socioId;
		
	}
}
