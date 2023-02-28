package br.edu.projeto.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Servico {
	
	@Id
	private Integer num_servico;
	
	@NotNull
	@CPF
	@Column(name = "cpfc")
	private String cpf;
	
	@Size (min = 1, max = 15, message = "O usuario deve conter entre 1 e 15 caracteres!!!")
	@Pattern(regexp = "[^0-9]*", message = "O nome de usuário não pode conter digitos.")
	@NotNull
	@Column(name = "func_user")
	private String fusername;
	
	@NotNull
	@Positive
	private Float horas_trb;
	
	@NotNull
	@Positive
	private Float preco_mobra;
	
	
	public Integer getNum_servico() {
		return num_servico;
	}

	public void setNum_servico(Integer num_servico) {
		this.num_servico = num_servico;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUsername() {
		return fusername;
	}

	public void setUsername(String fusername) {
		this.fusername = fusername;
	}

	public Float getHoras_trb() {
		return horas_trb;
	}

	public void setHoras_trb(Float horas_trb) {
		this.horas_trb = horas_trb;
	}

	public Float getPreco_mobra() {
		return preco_mobra;
	}

	public void setPreco_mobra(Float preco_mobra) {
		this.preco_mobra = preco_mobra;
	}

	
}
