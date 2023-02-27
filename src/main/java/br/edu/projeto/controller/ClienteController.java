package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.ClienteDAO;
import br.edu.projeto.model.Cliente;

@ViewScoped
@Named
public class ClienteController implements Serializable{
	
	@Inject
	private FacesContext facesContext;
	@Inject
	private ClienteDAO clienteDAO;
	private Cliente novoCliente;
	private List<Cliente> listaClientes;
	
	@PostConstruct
	public void inicializarCliente() {
		novoCliente = new Cliente();
		listaClientes = clienteDAO.listarTodos();
	}
	
	public void cadastrarCliente() {
		this.setNovoCliente(new Cliente());
	}
	
	public void atualizarCliente() {
		this.setNovoCliente(new Cliente());
	}
	
	public void removerCliente() {
		this.setNovoCliente(new Cliente());
	}
	
	public void register() throws Exception{
		if (clienteValido()) {
			try {
				if (!clienteDAO.clienteExiste(clienteDAO.findByCpf(novoCliente))){
					clienteDAO.salvar(novoCliente);
					facesContext.addMessage(null, new FacesMessage("Cliente Cadastrado"));
				}else {
					facesContext.addMessage(null, new FacesMessage("Já há um cliente com esse cpf cadastrado"));
				}
				inicializarCliente();
			    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
	        } catch (Exception e) {
				String errorMessage = getRootErrorMessage(e);
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
	        }
		}
	}
	

	public void update() throws Exception{
	try {
			if (clienteDAO.clienteExiste(clienteDAO.findByCpf(novoCliente))) {
				clienteDAO.atualizar(novoCliente);
				inicializarCliente();
				facesContext.addMessage(null, new FacesMessage("Cliente Atualizado"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Não há cliente com esse cpf cadastrado"));
			}
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
		}catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
		}
	}
	public void delete() throws Exception{
		try {
			if (clienteDAO.clienteExiste(clienteDAO.findByCpf(novoCliente))) {
				clienteDAO.excluir(novoCliente);
				novoCliente = null;
				inicializarCliente();
				facesContext.addMessage(null, new FacesMessage("Cliente Excluído"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Não há cliente com esse cpf cadastrado"));
			}
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
		}catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
		}
	}
	
	private boolean clienteValido() {
		if (this.clienteDAO.isClienteUnico(this.novoCliente.getCpf())) return true;
		return false;
	}
	
//GET ERROR MSG
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
//GETS E SETS
	public Cliente getNovoCliente() {
		return novoCliente;
	}

	public void setNovoCliente(Cliente novoCliente) {
		this.novoCliente = novoCliente;
	}

	public List<Cliente> getListaClientes() {
		if (listaClientes == null) {
			listaClientes = clienteDAO.listarTodos();
		}
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
	
	
	
}
