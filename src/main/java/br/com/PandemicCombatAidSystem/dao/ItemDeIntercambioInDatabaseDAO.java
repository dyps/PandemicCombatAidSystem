package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.ItemDeIntercambio;

public interface ItemDeIntercambioInDatabaseDAO extends JpaRepository<ItemDeIntercambio, Integer> {

}
