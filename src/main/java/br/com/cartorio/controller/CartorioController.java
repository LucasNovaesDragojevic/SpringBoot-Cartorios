package br.com.cartorio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cartorio.model.Cartorio;
import br.com.cartorio.repository.CartorioRepository;

@Controller
@RequestMapping("cartorios")
public class CartorioController 
{
	@Autowired
	private CartorioRepository cartorioRepository;
	
	@GetMapping
	public String list(Model model)
	{
		List<Cartorio> lista = cartorioRepository.findAll();
		model.addAttribute("cartorios", lista);
		return "index";
	}
}
