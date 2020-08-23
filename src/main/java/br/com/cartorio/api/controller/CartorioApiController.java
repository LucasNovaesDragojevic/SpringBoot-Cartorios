package br.com.cartorio.api.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cartorio.model.Cartorio;
import br.com.cartorio.repository.CartorioRepository;

@Transactional
@RestController
@RequestMapping("api/cartorios")
public class CartorioApiController 
{
	@Autowired
	private CartorioRepository cartorioRepository;
	
	@GetMapping
	public Page<Cartorio> list(Pageable pagination)
	{
		return cartorioRepository.findAll(pagination);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Cartorio> getById(@PathVariable Long id)
	{
		Optional<Cartorio> cartorio = cartorioRepository.findById(id);
		if (cartorio.isPresent())
		{
			return ResponseEntity.ok(cartorio.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cartorio> create(@RequestBody Cartorio cartorio, UriComponentsBuilder uriComponentsBuilder)
	{
		cartorio = cartorioRepository.save(cartorio);
		URI uri = uriComponentsBuilder.path("/api/cartorios/{id}").buildAndExpand(cartorio.getId()).toUri();
		return ResponseEntity.created(uri).body(cartorio);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cartorio> update(@PathVariable Long id, @RequestBody Cartorio newCartorio)
	{
		Optional<Cartorio> cartorio = cartorioRepository.findById(id);
		if (cartorio.isPresent())
		{
			Cartorio cartorioUpdated = cartorio.get();
			cartorioUpdated.setName(newCartorio.getName());
			cartorioUpdated.setStreet(newCartorio.getStreet());
			cartorioUpdated.setNumber(newCartorio.getNumber());
			cartorioUpdated.setCity(newCartorio.getCity());
			cartorioUpdated.setState(newCartorio.getState());
			cartorioUpdated.setZip(newCartorio.getZip());
			return ResponseEntity.ok(cartorioUpdated);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> remove(@PathVariable Long id)
	{
		Optional<Cartorio> cartorio = cartorioRepository.findById(id);
		if (cartorio.isPresent())
		{
			cartorioRepository.delete(cartorio.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
