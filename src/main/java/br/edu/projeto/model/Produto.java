package br.edu.projeto.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Produto {

	@Id
	@NotNull
	@Positive
	private Integer codigo;
	
	@NotNull
	@Size (min = 2, max = 15, message = "O nome deve conter entre 2 e 15 caracteres")
	private String nome;
	
	@NotNull
	private String categoria;
	
	@PositiveOrZero
	@NotNull
	private Float preco;
	
	@PositiveOrZero
	@NotNull
	private Integer quantidade;
	
	@NotNull
	@Size (min = 3, max = 30, message = "Máximo de 30 caracteres para a descrição do produto")
	private String descr;

	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
