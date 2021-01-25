package br.com.PandemicCombatAidSystem.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.PandemicCombatAidSystem.service.ServicePandemicCombatAidSystemException;

@ControllerAdvice
public class PandemicCombatAidSystemExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String mensagemUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesen = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> listaDeErros = Arrays.asList(new Erro(mensagemUser, mensagemDesen));
		return handleExceptionInternal(ex, listaDeErros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		List<Erro> listaDeErros = listaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, listaDeErros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class, NoSuchElementException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(Exception ex, WebRequest request) {
		String mensagemUser = messageSource.getMessage("nao.encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesen = ex.toString();
		List<Erro> listaDeErros = Arrays.asList(new Erro(mensagemUser, mensagemDesen));
		return handleExceptionInternal(ex, listaDeErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		String mensagemUser = messageSource.getMessage("chave.duplicada", null, LocaleContextHolder.getLocale());
		String mensagemDesen = ex.toString();
		List<Erro> listaDeErros = Arrays.asList(new Erro(mensagemUser, mensagemDesen));
		return handleExceptionInternal(ex, listaDeErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}
	@ExceptionHandler(ServicePandemicCombatAidSystemException.class)
	public ResponseEntity<Object> handleServicePandemicCombatAidSystemException(ServicePandemicCombatAidSystemException ex, WebRequest request) {
		String mensagemUser = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
		String mensagemDesen = ex.toString();
		List<Erro> listaDeErros = Arrays.asList(new Erro(mensagemUser, mensagemDesen));
		return handleExceptionInternal(ex, listaDeErros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	private List<Erro> listaErros(BindingResult bindingResult) {
		List<Erro> list = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {

			String mensagemUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDev = fieldError.toString();
			list.add(new Erro(mensagemUser, mensagemDev));
		}
		return list;

	}

	public static class Erro {
		private String mensagemUser;
		private String mensagemDev;

		public Erro(String mensagemUser, String mensagemDev) {
			this.mensagemUser = mensagemUser;
			this.mensagemDev = mensagemDev;
		}

		public String getMensagemUser() {
			return mensagemUser;
		}

		public String getMensagemDev() {
			return mensagemDev;
		}

	}
}
