package br.edu.projeto.model;

import javax.persistence.Entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "lista_tarefa")
public class ListaTarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num_tarefa;
	
	@Size (min = 1, max = 15, message = "O usuario deve conter entre 1 e 15 caracteres!!!")
	@Pattern(regexp = "[^0-9]*", message = "O nome de usuário não pode conter digitos.")
	@NotNull
	@Column(name = "func_resp")
	private String fusername;
	
	@NotNull
	@Size(min = 3, max = 15, message =  "O nome da tarefa deve conter entre 3 e 15 caracteres!!!")
	private String nome;
	
	@NotNull
	@Column (name = "descr")
	@Size(min = 3, max = 100, message =  "A descrição deve conter entre 3 e 100 caracteres!!!")
	private String descricao;
	
	
	private Date data_inicio;

	public Integer getNum_tarefa() {
		return num_tarefa;
	}

	public void setNum_tarefa(Integer num_tarefa) {
		this.num_tarefa = num_tarefa;
	}

	public String getFusername() {
		return fusername;
	}

	public void setFusername(String fusername) {
		this.fusername = fusername;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDesricao() {
		return descricao;
	}

	public void setDesricao(String desricao) {
		this.descricao = desricao;
	}

	public Date getData_inicio() {
		return data_inicio;
	}

	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}
	
	
}
