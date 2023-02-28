package br.edu.projeto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categoria_produtos")
public class CategoriaProduto {

	@Id
	@NotNull
	@Size (min = 3, max = 15)
	@Pattern (regexp = "[^0-9]*", message = "O nome nao pode conter digitos")
	private String nome;
	
	@NotNull
	@Column (name = "descr")
	@Size (min = 3, max = 40)
	private String descricao;
	
	@Size (max = 15)
	private String categoriamae;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		if (categoriamae.isBlank() || categoriamae.isEmpty() || categoriamae.length() == 0) {
			categoriamae = null;
		}
		this.categoriamae = categoriamae;
	}
	
}
