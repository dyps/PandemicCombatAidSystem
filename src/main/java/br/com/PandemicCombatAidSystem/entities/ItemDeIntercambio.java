package br.com.PandemicCombatAidSystem.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_ItemDeIntercambio")
public class ItemDeIntercambio implements Identificavel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ItemDeIntercambioSeq")
	@SequenceGenerator(name = "ItemDeIntercambioSeq", sequenceName = "ITEMINTERC_SEQ", allocationSize = 1)
	private Integer Id;

	@NotNull
	private int quantidade;

	@ManyToOne
	@NotNull
	private Hospital hospital;

	@ManyToOne
	@NotNull
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

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
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
		result = prime * result + ((hospital == null) ? 0 : hospital.hashCode());
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
		ItemDeIntercambio other = (ItemDeIntercambio) obj;
		if (hospital == null) {
			if (other.hospital != null)
				return false;
		} else if (!hospital.equals(other.hospital))
			return false;
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
