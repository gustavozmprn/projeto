package br.edu.projeto.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.projeto.dao.ListaTarefaDAO;
import br.edu.projeto.model.ListaTarefa;

@RequestScoped
@Named
public class ListaTarefaController {

	@Inject
	private FacesContext facesContext;
	@Inject
	private ListaTarefaDAO listaTarefaDAO;
	private ListaTarefa novaListaTarefa;
	private List<ListaTarefa> listaDeTarefas;
	
	@PostConstruct
	public void inicializarTarefa() {
		novaListaTarefa = new ListaTarefa();
		listaDeTarefas = listaTarefaDAO.listarTodos();
	}
	
	public void cadastrarTarefa() {
		this.setNovaListaTarefa(new ListaTarefa());
	}
	
	public void atualizarTarefa() {
		this.setNovaListaTarefa(new ListaTarefa());
	}
	
	public void excluirTarefa() {
		this.setNovaListaTarefa(new ListaTarefa());
	}
	
	public void register() throws Exception{
		try {
			if (tarefaValida()) {
	            listaTarefaDAO.salvar(novaListaTarefa);
	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
	            facesContext.addMessage(null, m);
			}else {
				facesContext.addMessage(null, new FacesMessage("Já existe uma tarefa com esse nome cadastrada"));
			}
            inicializarTarefa();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            if (errorMessage.contains("violates foreign key constraint")) {
            	errorMessage = "Não existe funcionário com esse username cadastrado";
            }
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
	}
	
	
	private boolean tarefaValida() {
		return !listaTarefaDAO.existeNome(this.novaListaTarefa);
	}
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

	public ListaTarefa getNovaListaTarefa() {
		return novaListaTarefa;
	}

	public void setNovaListaTarefa(ListaTarefa novaListaTarefa) {
		this.novaListaTarefa = novaListaTarefa;
	}

	public List<ListaTarefa> getListaDeTarefas() {
		if (listaDeTarefas == null) {
			listaDeTarefas = listaTarefaDAO.listarTodos();
		}
		return listaDeTarefas;
	}

	public void setListaDeTarefas(List<ListaTarefa> listaDeTarefas) {
		this.listaDeTarefas = listaDeTarefas;
	}
	
	
	
}
