package br.com.PandemicCombatAidSystem.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.PandemicCombatAidSystem.dao.HospitalInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.Hospital;
import br.com.PandemicCombatAidSystem.entities.PercentualDeOcupacao;
import br.com.PandemicCombatAidSystem.event.EntidadeCriadaEvent;
import br.com.PandemicCombatAidSystem.service.HospitalService;
import br.com.PandemicCombatAidSystem.service.ServicePandemicCombatAidSystemException;

@RestController
@RequestMapping("/hospital")
public class HospitalResource {

	@Autowired
	private HospitalInDatabaseDAO hospitalInDatabaseDAO;
	@Autowired
	private HospitalService hospitalService;
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@GetMapping
	public List<Hospital> listar() {
		return hospitalInDatabaseDAO.findAll();
	}

	@PostMapping
	public ResponseEntity<Hospital> salvar(@Valid @RequestBody Hospital entity, HttpServletResponse response)
			throws ServicePandemicCombatAidSystemException {

		Hospital hospitalSalvo = hospitalService.salvar(entity);
		applicationEventPublisher.publishEvent(new EntidadeCriadaEvent(this, response, hospitalSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(hospitalSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Hospital> buscarPorId(@PathVariable Integer id) {
		Optional<Hospital> tipoDeRecurso = hospitalInDatabaseDAO.findById(id);
		return ResponseEntity.ok(tipoDeRecurso.get());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) throws ServicePandemicCombatAidSystemException {
		hospitalService.delete(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Hospital> atualizar(@PathVariable Integer id, @Valid @RequestBody Hospital entity)
			throws ServicePandemicCombatAidSystemException {
		Hospital tipoDeRecurso = hospitalService.salvar(id, entity);
		return ResponseEntity.ok(tipoDeRecurso);
	}

	@PostMapping("/{id}/ocupacao")
	public ResponseEntity<Hospital> adicionarPercentualDeOcupacao(@PathVariable Integer id, @Valid @RequestBody PercentualDeOcupacao entity,
			HttpServletResponse response) throws ServicePandemicCombatAidSystemException {

		Hospital hospitalSalvo = hospitalService.adicionarPercentualDeOcupacao(id,entity);
		applicationEventPublisher.publishEvent(new EntidadeCriadaEvent(this, response, hospitalSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(hospitalSalvo);
	}

}
