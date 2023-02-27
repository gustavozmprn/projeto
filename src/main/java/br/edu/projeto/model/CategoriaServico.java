package br.edu.projeto.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



@Entity
@Table(name = "categoria_servicos")
public class CategoriaServico {
	@Id
	@NotNull
	@Size (min = 3, max = 15)
	@Pattern (regexp = "[^0-9]*", message = "O nome nao pode conter digitos")
	private String nome;
	
	@NotNull
	private Integer qtd_itens;
	
	@NotNull
	@Size (min = 3, max = 40)
	private String descricao;
	
	@Size (min = 3, max = 15)
	private String categoriamae;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtd_itens() {
		return qtd_itens;
	}

	public void setQtd_itens(Integer qtd_itens) {
		this.qtd_itens = qtd_itens;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoriamae() {
		return categoriamae;
	}

	public void setCategoriamae(String categoriamae) {
		this.categoriamae = categoriamae;
	}
	
	
}
