package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.FuncionarioDAO;
import br.edu.projeto.dao.TipoPermissaoDAO;
import br.edu.projeto.model.Funcionario;
import br.edu.projeto.model.TipoPermissao;

@ViewScoped
@Named

public class FuncionarioController implements Serializable {
	@Inject
	transient private Pbkdf2PasswordHash passwordHash;
	@Inject
	private FacesContext facesContext;
	@Inject
	private FuncionarioDAO funcionarioDAO;
	@Inject
	private TipoPermissaoDAO tipoPermissaoDAO;
	private Funcionario novoFuncionario;
	private List<Funcionario> listaFuncionarios;
	private List<SelectItem> permissoes;
	private List<Integer> permissoesSelecionadas;
	
	@PostConstruct
	public void inicializarFuncionario() {
		permissoesSelecionadas = new ArrayList<Integer>();
		listaFuncionarios = funcionarioDAO.listarTodos();
		permissoes = new ArrayList<SelectItem>();
    	for (TipoPermissao p: this.tipoPermissaoDAO.listarTodos()) {
    		SelectItem i = new SelectItem(p.getPermissao().id, p.getPermissao().name());		
    		this.permissoes.add(i);
    	}
	}
	
	public void novoCadastro() {
		this.setNovoFuncionario(new Funcionario());
	}
	public void atualizarCadastro() {
		this.setNovoFuncionario(new Funcionario());
	}
	
	public void register() throws Exception{
		if (funcionarioValido()) {
			this.novoFuncionario.getPermissoes().clear();
    		for (Integer id: this.permissoesSelecionadas) {
    			TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(id);
    			permissao.addFuncionario(this.novoFuncionario);
    		}
			try {
				this.novoFuncionario.setSenha(this.passwordHash.generate(this.novoFuncionario.getSenha().toCharArray()));
				if (funcionarioDAO.findByUsername(novoFuncionario).isEmpty()) {
					funcionarioDAO.salvar(novoFuncionario);
					facesContext.addMessage(null, new FacesMessage("Funcionário Criado"));
				}else {
					facesContext.addMessage(null, new FacesMessage("Já há um funcionário com esse username cadastrado!"));
				}
				listaFuncionarios = funcionarioDAO.listarTodos();
			    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
			}catch (Exception e) {
				String errorMessage = getRootErrorMessage(e);
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
			}
		}
            
	}
	
	public void update() throws Exception{
		if (funcionarioDAO.funcionarioExiste(funcionarioDAO.findByUsername(novoFuncionario))) {
			this.novoFuncionario.getPermissoes().clear();
			for (Integer id: this.permissoesSelecionadas) {
				TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(id);
				permissao.addFuncionario(this.novoFuncionario);
			}
			try {
				this.novoFuncionario.setSenha(this.passwordHash.generate(this.novoFuncionario.getSenha().toCharArray()));
				funcionarioDAO.atualizar(novoFuncionario);
				facesContext.addMessage(null, new FacesMessage("Funcionário Atualizado"));
				listaFuncionarios = funcionarioDAO.listarTodos();
			    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
			}catch (Exception e) {
				String errorMessage = getRootErrorMessage(e);
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
			}
		}
	}
	
	public void delete() throws Exception{
		if (funcionarioDAO.funcionarioExiste(funcionarioDAO.findByUsername(novoFuncionario))) {
			try {
					funcionarioDAO.excluir(novoFuncionario);
					listaFuncionarios = funcionarioDAO.listarTodos();
					this.novoFuncionario = null;
			}catch (Exception e) {
				String errorMessage = getRootErrorMessage(e);
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
			}
		}
	}

	private boolean funcionarioValido() {
		if (this.funcionarioDAO.isFuncionarioUnico(this.novoFuncionario.getUsername())) {
			return true;
		}
		return false;
	}
	
	///GET ERROR MESSAGE
	private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }
	
	///GETS E SETS
	public Funcionario getNovoFuncionario() {
		return novoFuncionario;
	}

	public void setNovoFuncionario(Funcionario novoFuncionario) {
		this.novoFuncionario = novoFuncionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		if (listaFuncionarios == null) {
			listaFuncionarios = funcionarioDAO.listarTodos();
		}
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<SelectItem> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<SelectItem> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Integer> getPermissoesSelecionadas() {
		return permissoesSelecionadas;
	}

	public void setPermissoesSelecionadas(List<Integer> permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}
	
}
