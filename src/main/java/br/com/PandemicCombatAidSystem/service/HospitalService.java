package br.com.PandemicCombatAidSystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.PandemicCombatAidSystem.dao.HospitalInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.ItemDeIntercambioInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.PercentualDeOcupacaoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.RecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.TipoDeRecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.Hospital;
import br.com.PandemicCombatAidSystem.entities.ItemDeIntercambio;
import br.com.PandemicCombatAidSystem.entities.PercentualDeOcupacao;
import br.com.PandemicCombatAidSystem.entities.Recurso;

@Service
public class HospitalService {
	@Autowired
	private ItemDeIntercambioInDatabaseDAO itemDeIntercambioInDatabaseDAO;

	@Autowired
	private HospitalInDatabaseDAO hospitalInDatabaseDAO;
	@Autowired
	private PercentualDeOcupacaoInDatabaseDAO percentualDeOcupacaoInDatabaseDAO;
	@Autowired
	private RecursoInDatabaseDAO recursoInDatabaseDAO;
	@Autowired
	private TipoDeRecursoInDatabaseDAO tipoDeRecursoInDatabaseDAO;

	public boolean repetido(Hospital entity) {
		for (Hospital hospital : hospitalInDatabaseDAO.findAll()) {
			if (hospital.equals(entity)) {
				return true;
			}
		}
		return false;
	}

	public Hospital salvar(Integer id, Hospital entity) throws ServicePandemicCombatAidSystemException {
		if (repetido(entity))
			throw new ServicePandemicCombatAidSystemException("valor.repetido");
		hospitalInDatabaseDAO.save(ignorar(entity, hospitalInDatabaseDAO.findById(id).get()));
		return entity;

	}

	public Hospital salvar(Hospital entity) throws ServicePandemicCombatAidSystemException {
		if (repetido(entity))
			throw new ServicePandemicCombatAidSystemException("valor.repetido");
		if (entity.getPercentuaisDeOcupacao().size() != 1)
			throw new ServicePandemicCombatAidSystemException("necessario.ocupacao");
		if (entity.getPercentuaisDeOcupacao().get(0).getPersentual() > 100
				|| entity.getPercentuaisDeOcupacao().get(0).getPersentual() < 0)
			throw new ServicePandemicCombatAidSystemException("mensagem.invalida");
		if (entity.getRecursos() == null || entity.getRecursos().size() < 1)
			throw new ServicePandemicCombatAidSystemException("necessario.recurso");

		adicionarPercentual(entity);

		adicionarRecursos(entity);

		return hospitalInDatabaseDAO.save(ignorarId(entity, new Hospital()));
	}

	private void adicionarRecursos(Hospital entity) throws ServicePandemicCombatAidSystemException {
		List<Recurso> listRecurso = new ArrayList<Recurso>();
		for (Recurso recurso : entity.getRecursos()) {
			if (recurso.getTipoDeRecurso() == null)
				throw new ServicePandemicCombatAidSystemException("Tipo de recurso nao especificado");
			if (recurso.getTipoDeRecurso().getId() == null)
				throw new ServicePandemicCombatAidSystemException("Tipo de recurso nao especificado");
			recurso.setTipoDeRecurso(tipoDeRecursoInDatabaseDAO.findById(recurso.getTipoDeRecurso().getId()).get());

			listRecurso.add(recursoInDatabaseDAO.save(ignorar(recurso, new Recurso())));
		}
		entity.setRecursos(listRecurso);
	}

	private void adicionarPercentual(Hospital entity) {
		List<PercentualDeOcupacao> list = new ArrayList<PercentualDeOcupacao>();
		for (PercentualDeOcupacao percentualDeOcupacao : entity.getPercentuaisDeOcupacao()) {
			PercentualDeOcupacao perc = ignorar(percentualDeOcupacao, new PercentualDeOcupacao());
			perc.setData(new Date());
			list.add(percentualDeOcupacaoInDatabaseDAO.save(perc));
		}
		entity.setPercentuaisDeOcupacao(list);
	}

	private Recurso ignorar(Recurso atualizacao, Recurso novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id");
		return novo;
	}

	private PercentualDeOcupacao ignorar(PercentualDeOcupacao atualizacao, PercentualDeOcupacao novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id", "data");
		return novo;
	}

	private Hospital ignorarId(Hospital atualizacao, Hospital novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id");
		return novo;
	}

	private Hospital ignorar(Hospital atualizacao, Hospital novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id", "percentuaisDeOcupacao", "recursos");
		return novo;
	}

	public void delete(Integer id) throws ServicePandemicCombatAidSystemException {
		for (ItemDeIntercambio itemDeIntercambio : itemDeIntercambioInDatabaseDAO.findAll()) {
			if (itemDeIntercambio.getHospital().getId() == id)
				throw new ServicePandemicCombatAidSystemException("nao.deletavel");
		}
		if (hospitalInDatabaseDAO.findById(id).get().getPercentuaisDeOcupacao().size() > 1)
			throw new ServicePandemicCombatAidSystemException("nao.deletavel");

		tipoDeRecursoInDatabaseDAO.deleteById(id);

	}

	public Hospital adicionarPercentualDeOcupacao(Integer id, PercentualDeOcupacao entity)
			throws ServicePandemicCombatAidSystemException {
		if (entity == null || entity.getPersentual() == null || entity.getPersentual() > 100
				|| entity.getPersentual() < 0)
			throw new ServicePandemicCombatAidSystemException("mensagem.invalida");
		Hospital hospital = hospitalInDatabaseDAO.findById(id).get();
		PercentualDeOcupacao perc = ignorar(entity, new PercentualDeOcupacao());
		perc.setData(new Date());
		hospital.getPercentuaisDeOcupacao().add(percentualDeOcupacaoInDatabaseDAO.save(perc));
		return hospitalInDatabaseDAO.save(hospital);
	}

}
