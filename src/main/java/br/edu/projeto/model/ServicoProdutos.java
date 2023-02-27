package br.edu.projeto.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Entity
@Table (name = "produtos_servicos")
public class ServicoProdutos {
	@Id
	private Integer num_servico;
	
	@NotNull
	private Integer codigo_produto;
	
	@NotNull
	@Positive
	private Integer qtd_produto;
	
	@NotNull
	@Positive
	private Float preco_produto;

	public Integer getNum_servico() {
		return num_servico;
	}

	public void setNum_servico(Integer num_servico) {
		this.num_servico = num_servico;
	}

	public Integer getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(Integer codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public Integer getQtd_produto() {
		return qtd_produto;
	}

	public void setQtd_produto(Integer qtd_produto) {
		this.qtd_produto = qtd_produto;
	}

	public Float getPreco_produto() {
		return preco_produto;
	}

	public void setPreco_produto(Float preco_produto) {
		this.preco_produto = preco_produto;
	}
	
	
}
