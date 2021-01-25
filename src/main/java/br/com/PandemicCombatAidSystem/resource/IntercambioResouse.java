 package br.com.PandemicCombatAidSystem.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.PandemicCombatAidSystem.dao.IntercambioInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.Intercambio;
import br.com.PandemicCombatAidSystem.event.EntidadeCriadaEvent;
import br.com.PandemicCombatAidSystem.service.IntercambioService;
import br.com.PandemicCombatAidSystem.service.ServicePandemicCombatAidSystemException;

@RestController
@RequestMapping("/intercambio")
public class IntercambioResouse {
	@Autowired
	private IntercambioInDatabaseDAO intercambioInDatabaseDAO;

	@Autowired
	private IntercambioService intercambioService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@GetMapping
	public List<Intercambio> listar() {
		return intercambioInDatabaseDAO.findAll();
	}

	@PostMapping
	public ResponseEntity<Intercambio> salvar(@Valid @RequestBody Intercambio entity, HttpServletResponse response)
			throws ServicePandemicCombatAidSystemException {

		Intercambio intercambio = intercambioService.salvar(entity);
		applicationEventPublisher.publishEvent(new EntidadeCriadaEvent(this, response, intercambio.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(intercambio);
	}

}
