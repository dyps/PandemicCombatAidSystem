package br.com.PandemicCombatAidSystem.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.PandemicCombatAidSystem.dao.HospitalInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.IntercambioInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.ItemDeIntercambioInDatabaseDAO;
import br.com.PandemicCombatAidSystem.dao.TipoDeRecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.Hospital;
import br.com.PandemicCombatAidSystem.entities.Intercambio;
import br.com.PandemicCombatAidSystem.entities.ItemDeIntercambio;
import br.com.PandemicCombatAidSystem.entities.PercentualDeOcupacao;
import br.com.PandemicCombatAidSystem.entities.Recurso;

@Service
public class IntercambioService {

	@Autowired
	private IntercambioInDatabaseDAO intercambioInDatabaseDAO;
	@Autowired
	private HospitalInDatabaseDAO hospitalInDatabaseDAO;
	@Autowired
	private TipoDeRecursoInDatabaseDAO tipoDeRecursoInDatabaseDAO;
	@Autowired
	private ItemDeIntercambioInDatabaseDAO itemDeIntercambioInDatabaseDAO;

	public Intercambio salvar(@Valid Intercambio entity) throws ServicePandemicCombatAidSystemException {
		entity = ignorarIdEData(entity, new Intercambio());
		entity.setData(new Date());
		List<ItemDeIntercambio> list = new ArrayList<>();
		if (entity.getItensDeIntercambio().size() < 2)
			throw new ServicePandemicCombatAidSystemException("necessario.itens");

		List<Hospital> listHospitais = new ArrayList<Hospital>();
		List<Integer> listPontuacaoHospitais = new ArrayList<Integer>();

		for (ItemDeIntercambio itemDeIntercambio : entity.getItensDeIntercambio()) {
			validarItem(itemDeIntercambio);
			Hospital hosp = hospitalInDatabaseDAO.findById(itemDeIntercambio.getHospital().getId()).get();
			if (!listHospitais.contains(hosp))
				listHospitais.add(hosp);
			int indice = indicedoHospital(listHospitais, hosp);
			Integer valor = null;
			try {
				valor = listPontuacaoHospitais.get(indice);

			} catch (Exception e) {
			}
			itemDeIntercambio.setTipoDeRecurso(
					tipoDeRecursoInDatabaseDAO.findById(itemDeIntercambio.getTipoDeRecurso().getId()).get());
			if (valor == null || valor <= 0) {
				listPontuacaoHospitais.add(indice,
						itemDeIntercambio.getQuantidade() * itemDeIntercambio.getTipoDeRecurso().getPontuacao());
			} else {
				listPontuacaoHospitais.set(indice,
						itemDeIntercambio.getQuantidade() * itemDeIntercambio.getTipoDeRecurso().getPontuacao()
								+ listPontuacaoHospitais.get(indice));
			}
			itemDeIntercambio.setHospital(hosp);
			itemDeIntercambio.setTipoDeRecurso(
					tipoDeRecursoInDatabaseDAO.findById(itemDeIntercambio.getTipoDeRecurso().getId()).get());

			hospitalTemQtd(itemDeIntercambio, hosp);

			list.add(itemDeIntercambioInDatabaseDAO.save(itemDeIntercambio));
		}
		if (listHospitais.size() != 2)
			throw new ServicePandemicCombatAidSystemException("necessario.hospitais");
		Integer primero = null;
		boolean valoresDiferentes = false;
		for (int i = 0; i < listPontuacaoHospitais.size(); i++) {
			Integer integer = listPontuacaoHospitais.get(i);
			if (i == 0) {
				primero = integer;
			} else if (integer != primero)
				valoresDiferentes = true;
		}

		boolean maior90 = false;
		for (Hospital hospital : listHospitais) {
			PercentualDeOcupacao percentualDeOcupacao = hospital.getPercentuaisDeOcupacao()
					.get(hospital.getPercentuaisDeOcupacao().size() - 1);
			if (maior90 == false)
				maior90 = percentualDeOcupacao.getPersentual() >= 90;
		}

		entity.setItensDeIntercambio(list);

		if (valoresDiferentes) {
			if (maior90) {
				intercambioInDatabaseDAO.save(entity);
			} else {
				throw new ServicePandemicCombatAidSystemException("valores.diferentes");
			}

		} else {
			intercambioInDatabaseDAO.save(entity);
		}
		return entity;
	}

	private int indicedoHospital(List<Hospital> listHospitais, Hospital hosp) {
		for (int i = 1; i < listHospitais.size(); i++) {
			if (hosp.equals(listHospitais.get(i))) {
				return i;
			}
		}
		return 0;
	}

	private void hospitalTemQtd(ItemDeIntercambio itemDeIntercambio, Hospital hosp)
			throws ServicePandemicCombatAidSystemException {
		for (Recurso recurso : hosp.getRecursos()) {
			if (recurso.getTipoDeRecurso().equals(itemDeIntercambio.getTipoDeRecurso())) {
				if (recurso.getQuantidade() < itemDeIntercambio.getQuantidade())
					throw new ServicePandemicCombatAidSystemException("qtd.maior");
				else
					return;
			}
		}
		throw new ServicePandemicCombatAidSystemException("qtd.maior");
	}

	private void validarItem(@Valid ItemDeIntercambio itemDeIntercambio)
			throws ServicePandemicCombatAidSystemException {
		if (itemDeIntercambio.getHospital() == null)
			throw new ServicePandemicCombatAidSystemException("necessario.id.hospital");
		if (itemDeIntercambio.getTipoDeRecurso() == null)
			throw new ServicePandemicCombatAidSystemException("necessario.id.tipoderecurso");
		if (itemDeIntercambio.getQuantidade() <= 0)
			throw new ServicePandemicCombatAidSystemException("necessario.qtd.maior");

	}

	private Intercambio ignorarIdEData(Intercambio atualizacao, Intercambio novo) {
		BeanUtils.copyProperties(atualizacao, novo, "id", "data");
		return novo;
	}

}
