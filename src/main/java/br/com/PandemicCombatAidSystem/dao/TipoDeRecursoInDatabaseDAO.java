package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.TipoDeRecurso;

public interface TipoDeRecursoInDatabaseDAO extends JpaRepository<TipoDeRecurso, Integer> {

}
