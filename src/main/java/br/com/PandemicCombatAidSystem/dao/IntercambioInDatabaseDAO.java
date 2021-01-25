package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.Intercambio;

public interface IntercambioInDatabaseDAO extends JpaRepository<Intercambio, Integer>{

}
