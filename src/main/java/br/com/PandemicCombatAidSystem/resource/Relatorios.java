package br.com.PandemicCombatAidSystem.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.PandemicCombatAidSystem.dao.HospitalInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.Hospital;

@RestController
@RequestMapping("/relatorios")
public class Relatorios {
	@Autowired
	HospitalInDatabaseDAO HospitalInDatabaseDAO;

	@GetMapping("/ocupacaomaiorque90")
	public int ocupacaomaiorque90() {
		List<Hospital> list = HospitalInDatabaseDAO.findAll();
		int cont = 0;
		for (Hospital hospital : list) {
			if (hospital.getPercentuaisDeOcupacao().get(hospital.getPercentuaisDeOcupacao().size() - 1)
					.getPersentual() > 90) {
				cont++;
			}
		}
		return 100/list.size()* cont;
	}
}
