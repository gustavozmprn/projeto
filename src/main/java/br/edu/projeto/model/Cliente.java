package br.edu.projeto.model;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Cliente {
	@Id
	@CPF
	@NotNull
	private String cpf;
	
	@NotNull
	@Size (min = 3, max = 32)
	@Pattern(regexp = "[^0-9]*", message = "O nome não pode conter dígitos")
	private String nome;
    @NotNull
    @NotEmpty
    @Size (min = 1, max = 50)
    @Email(message = "Não é um endereço de E-mail válido")
    private String email;
    
    @NotNull
    @NotEmpty
    @Size (min = 1, max = 12)
    private String telefone;
    
	@NotNull
	private String logradouro;
	
	@NotNull
	@Size (min = 8, max = 8)
	private String CEP;
	
	@NotNull
	private Integer numero_endrc;
	
	@NotNull
	private String complemento;
	
	@Size(max = 60)
	private String descricao;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public Integer getNumero_endrc() {
		return numero_endrc;
	}

	public void setNumero_endrc(Integer numero_endrc) {
		this.numero_endrc = numero_endrc;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
