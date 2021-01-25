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

import br.com.PandemicCombatAidSystem.dao.TipoDeRecursoInDatabaseDAO;
import br.com.PandemicCombatAidSystem.entities.TipoDeRecurso;
import br.com.PandemicCombatAidSystem.event.EntidadeCriadaEvent;
import br.com.PandemicCombatAidSystem.service.ServicePandemicCombatAidSystemException;
import br.com.PandemicCombatAidSystem.service.TipoDeRecursoService;

@RestController
@RequestMapping("/tipoderecurso")
public class TipoDeRecursoResource {
	@Autowired
	private TipoDeRecursoInDatabaseDAO tipoDeRecursoInDatabaseDAO;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	private TipoDeRecursoService tipoDeRecursoService;

	@GetMapping
	public List<TipoDeRecurso> listar() {
		return tipoDeRecursoInDatabaseDAO.findAll();
	}

	@PostMapping
	public ResponseEntity<TipoDeRecurso> salvar(@Valid @RequestBody TipoDeRecurso entity,
			HttpServletResponse response) {
		if (tipoDeRecursoService.repetido(entity))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		TipoDeRecurso tipoDeRecursoSalvo = tipoDeRecursoService.salvar(entity);
		applicationEventPublisher.publishEvent(new EntidadeCriadaEvent(this, response, tipoDeRecursoSalvo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(tipoDeRecursoSalvo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TipoDeRecurso> buscarPorId(@PathVariable Integer id) {
		Optional<TipoDeRecurso> tipoDeRecurso = tipoDeRecursoInDatabaseDAO.findById(id);
		return ResponseEntity.ok(tipoDeRecurso.get());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) throws ServicePandemicCombatAidSystemException {
		tipoDeRecursoService.delete(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoDeRecurso> atualizar(@PathVariable Integer id, @Valid @RequestBody TipoDeRecurso entity) {
		if (tipoDeRecursoService.repetido(entity))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		TipoDeRecurso tipoDeRecurso = tipoDeRecursoService.salvar(id, entity);
		return ResponseEntity.ok(tipoDeRecurso);
	}
}
