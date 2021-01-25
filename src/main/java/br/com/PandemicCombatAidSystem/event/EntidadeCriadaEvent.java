package br.com.PandemicCombatAidSystem.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class EntidadeCriadaEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private Integer id;

	public EntidadeCriadaEvent(Object source, HttpServletResponse response, Integer id) {
		super(source);
		this.response = response;
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Integer getId() {
		return id;
	}

}
