package br.com.PandemicCombatAidSystem.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_PercentualDeOcupacao")
public class PercentualDeOcupacao implements Identificavel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PercentualDeOcupacaoSeq")
	@SequenceGenerator(name = "PercentualDeOcupacaoSeq", sequenceName = "PERCENTUAL_SEQ", allocationSize = 1)
	private Integer Id;

	@Column(nullable = false)
	@DateTimeFormat
	private Date data;
	
	@Column(nullable = false)
	private Integer persentual;

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

	public Integer getPersentual() {
		return persentual;
	}

	public void setPersentual(Integer persentual) {
		this.persentual = persentual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((persentual == null) ? 0 : persentual.hashCode());
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
		PercentualDeOcupacao other = (PercentualDeOcupacao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (persentual == null) {
			if (other.persentual != null)
				return false;
		} else if (!persentual.equals(other.persentual))
			return false;
		return true;
	}


	
}
