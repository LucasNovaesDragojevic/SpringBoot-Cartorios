package br.com.cartorio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cartorio.model.Cartorio;
import br.com.cartorio.repository.CartorioRepository;

@Controller
@RequestMapping("cartorios")
public class CartorioController 
{
	@Autowired
	private CartorioRepository cartorioRepository;
	
	@GetMapping
	public ModelAndView list()
	{
		List<Cartorio> cartorios = cartorioRepository.findAll();
		ModelAndView mv = new ModelAndView();
		mv.setStatus(HttpStatus.OK);
		mv.setViewName("/index");
		mv.addObject("cartorios", cartorios);
		return mv;
	}
	
	@GetMapping("{id:[0-9]}")
	public ModelAndView getById(@PathVariable String id)
	{
		Optional<Cartorio> cartorio = cartorioRepository.findById(Long.valueOf(id));
		ModelAndView mv = new ModelAndView();
		if (cartorio.isPresent())
		{
			mv.setStatus(HttpStatus.OK);
			mv.setViewName("/cartorio");
			mv.addObject(cartorio.get());
			return mv;
		}
		mv.setStatus(HttpStatus.NOT_FOUND);
		mv.setViewName("/error");
		return mv;
	}
	
	@GetMapping("create")
	public ModelAndView create()
	{
		Cartorio cartorio = new Cartorio();
		ModelAndView mv = new ModelAndView();
		mv.setStatus(HttpStatus.OK);
		mv.setViewName("/cartorio");
		mv.addObject(cartorio);
		return mv;
	}
	
	@PostMapping("save")
	public String save(@ModelAttribute("cartorio") Cartorio cartorio)
	{
		cartorioRepository.save(cartorio);
		return "redirect:/cartorios";
	}
	
	@GetMapping("delete/{id}")
	public String delete(@PathVariable String id)
	{
		cartorioRepository.deleteById(Long.valueOf(id));
		return "redirect:/cartorios";
	}
}
