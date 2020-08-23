package br.com.cartorio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cartorio.model.Cartorio;

public interface CartorioRepository extends JpaRepository<Cartorio, Long>
{

}
