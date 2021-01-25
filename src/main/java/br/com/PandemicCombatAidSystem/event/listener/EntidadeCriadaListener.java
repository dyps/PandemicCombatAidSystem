package br.com.PandemicCombatAidSystem.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.PandemicCombatAidSystem.event.EntidadeCriadaEvent;

@Component
public class EntidadeCriadaListener implements ApplicationListener<EntidadeCriadaEvent> {

	@Override
	public void onApplicationEvent(EntidadeCriadaEvent event) {
		HttpServletResponse response = event.getResponse();
		Integer id = event.getId();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("Location", uri.toASCIIString());

	}

}
