package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.Recurso;

public interface RecursoInDatabaseDAO extends JpaRepository<Recurso, Integer>{

}
