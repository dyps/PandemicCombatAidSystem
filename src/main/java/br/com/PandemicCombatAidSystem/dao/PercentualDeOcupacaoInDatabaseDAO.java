package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.PercentualDeOcupacao;

public interface PercentualDeOcupacaoInDatabaseDAO extends JpaRepository<PercentualDeOcupacao, Integer>{

}
