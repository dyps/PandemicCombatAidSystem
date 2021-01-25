package br.com.PandemicCombatAidSystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.PandemicCombatAidSystem.entities.Hospital;

public interface HospitalInDatabaseDAO extends JpaRepository<Hospital, Integer> {

}
