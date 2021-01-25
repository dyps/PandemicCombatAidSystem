package br.com.PandemicCombatAidSystem.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.PandemicCombatAidSystem.dao.ItemDeIntercambioInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.RecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.TipoDeRecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.ItemDeIntercambio;
import br.com.PandemicCombatAidSystem.entities.Recurso;
import br.com.PandemicCombatAidSystem.entities.TipoDeRecurso;

@Service
public class TipoDeRecursoService {
	@Autowired
	private TipoDeRecursoInDatabaseDAO tipoDeRecursoInDatabaseDAO;
	
	@Autowired
	private ItemDeIntercambioInDatabaseDAO itemDeIntercambioInDatabaseDAO;
	@Autowired
	private RecursoInDatabaseDAO recursoInDatabaseDAO;

	public boolean repetido(TipoDeRecurso entity) {
		for (TipoDeRecurso tipoDeRecurso : tipoDeRecursoInDatabaseDAO.findAll()) {
			if (tipoDeRecurso.equals(entity)) {
				return true;
			}
		}
		return false;
	}

	public <T> T ignorarID(T atualizacao, T novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id");
		return novo;
	}

	public TipoDeRecurso buscarPorId(Integer id) {
		return tipoDeRecursoInDatabaseDAO.findById(id).get();
	}
	
	public TipoDeRecurso salvar(Integer id, TipoDeRecurso entity) {
		tipoDeRecursoInDatabaseDAO.save(ignorarID(entity, buscarPorId(id)));
		return entity;

	}


	public TipoDeRecurso salvar(TipoDeRecurso entity) {
		return tipoDeRecursoInDatabaseDAO.save(ignorarID(entity, new TipoDeRecurso()));
	}
	
	public void delete(Integer id) throws ServicePandemicCombatAidSystemException {
		for (ItemDeIntercambio itemDeIntercambio : itemDeIntercambioInDatabaseDAO.findAll()) {
			if(itemDeIntercambio.getTipoDeRecurso().getId()==id)
				throw new ServicePandemicCombatAidSystemException("nao.deletavel");
		}
		for (Recurso recurso : recursoInDatabaseDAO.findAll()) {
			if(recurso.getTipoDeRecurso().getId()==id)
				throw new ServicePandemicCombatAidSystemException("nao.deletavel");
		}
		tipoDeRecursoInDatabaseDAO.deleteById(id);
		
	}

}
