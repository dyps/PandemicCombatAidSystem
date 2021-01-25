package br.com.PandemicCombatAidSystem.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "TB_Intercambio")
public class Intercambio implements Identificavel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IntercambioSeq")
	@SequenceGenerator(name = "IntercambioSeq", sequenceName = "INTERCAMBIO_SEQ", allocationSize = 1)
	private Integer Id;

	private Date data;

	@OneToMany
	@NotNull
	private List<ItemDeIntercambio> itensDeIntercambio;


	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<ItemDeIntercambio> getItensDeIntercambio() {
		return itensDeIntercambio;
	}

	public void setItensDeIntercambio(List<ItemDeIntercambio> itensDeIntercambio) {
		this.itensDeIntercambio = itensDeIntercambio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((itensDeIntercambio == null) ? 0 : itensDeIntercambio.hashCode());
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
		Intercambio other = (Intercambio) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (itensDeIntercambio == null) {
			if (other.itensDeIntercambio != null)
				return false;
		} else if (!itensDeIntercambio.equals(other.itensDeIntercambio))
			return false;
		return true;
	}
	


}
