package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Funcionario {

	@Id
	@Size (min = 1, max = 15, message = "O usuario deve conter entre 1 e 15 caracteres!!!")
	@Pattern(regexp = "[^0-9]*", message = "O nome de usuário não pode conter digitos.")
	@NotNull
	private String username;
	
	@NotNull
	@Size (min = 3, max = 32, message = "O nome pode ter ate no maximo 32 caracteres")
	@Pattern(regexp = "[^0-9]*", message = "O nome não pode conter digitos.")
	private String nome;


	@NotNull
	private String senha;
	
    @NotNull
    @NotEmpty
    @Size (min = 1, max = 50)
    @Email(message = "Não é um endereço de E-mail válido")
    private String email;
    
    @NotNull
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
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    	      name = "permissoes",
    	      joinColumns = @JoinColumn(name = "func_user"),
    	      inverseJoinColumns = @JoinColumn(name = "perm_id")
    	    )
    private List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
	
	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
