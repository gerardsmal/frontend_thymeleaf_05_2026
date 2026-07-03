package com.betacom.fe.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.betacom.fe.dto.input.UtenteReq;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.security.UtenteServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class RegisterController {
	private final WebClient webClient;
	private final UtenteServices utS;
	
	@GetMapping("/registry")
	public ModelAndView registry(Model model) {
		ModelAndView mav = new ModelAndView("registrazione");
		if (!model.containsAttribute("req")) {
			mav.addObject("req", new UtenteReq());			
		}
		return mav;
	}
	
	@PostMapping("/saveNuovoUtente")
	public String saveNuovoUtente(@ModelAttribute UtenteReq req, RedirectAttributes ra) {
		
		req.setRole("USER");
		log.debug("saveNuovoUtente {}", req);
		ResponseEntity<ResponseDTO> response = webClient.post()
				.uri("utente/create")
				.bodyValue(req)
				.exchangeToMono(resp -> resp.toEntity(ResponseDTO.class))
				.block();
		log.debug("response: {}", response.getBody().getMsg());
		
		if (!response.getStatusCode().is2xxSuccessful()) {
			
			ra.addFlashAttribute("errorMsg", response.getBody().getMsg());
			return "redirect:/registry";
		}
		utS.updateUtente(req);
		return "redirect:/login";
	}
}
