package br.com.PandemicCombatAidSystem.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TB_Recurso")
public class Recurso implements Identificavel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RecursoSeq")
	@SequenceGenerator(name = "RecursoSeq", sequenceName = "RECURSO_SEQ", allocationSize = 1)
	private Integer Id;

	private int quantidade;

	@ManyToOne
	private TipoDeRecurso tipoDeRecurso;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public TipoDeRecurso getTipoDeRecurso() {
		return tipoDeRecurso;
	}

	public void setTipoDeRecurso(TipoDeRecurso tipoDeRecurso) {
		this.tipoDeRecurso = tipoDeRecurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quantidade;
		result = prime * result + ((tipoDeRecurso == null) ? 0 : tipoDeRecurso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recurso other = (Recurso) obj;
		if (quantidade != other.quantidade)
			return false;
		if (tipoDeRecurso == null) {
			if (other.tipoDeRecurso != null)
				return false;
		} else if (!tipoDeRecurso.equals(other.tipoDeRecurso))
			return false;
		return true;
	}

}
