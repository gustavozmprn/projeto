package br.edu.projeto.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.CategoriaProdutoDAO;
import br.edu.projeto.model.CategoriaProduto;

@ViewScoped
@Named
public class CategoriaProdutoController implements Serializable{
	@Inject
	private FacesContext facesContext;
	@Inject
	private CategoriaProdutoDAO categoriaProdutoDAO;
	private CategoriaProduto novaCategoriaProduto;
	private List<CategoriaProduto> listaCategoriasProdutos;
	
	@PostConstruct
	
	public void inicializarCategoriaProduto() {
    	if (!this.facesContext.getExternalContext().isUserInRole("ADMINISTRADOR") && !this.facesContext.getExternalContext().isUserInRole("NORMAL")) {
    		try {
				this.facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
		novaCategoriaProduto = new CategoriaProduto();
		listaCategoriasProdutos = categoriaProdutoDAO.listarTodos();
	}
	
	public void cadastrarCategoria() {
		this.setNovaCategoriaProduto(new CategoriaProduto());
	}
	
	public void atualizarCategoria() {
		this.setNovaCategoriaProduto(new CategoriaProduto());
	}
	
	public void deletarCategoria() {
		this.setNovaCategoriaProduto(new CategoriaProduto());
	}
	
	public void register() throws Exception{
		try {
			if (!categoriaProdutoDAO.categoriaExiste(novaCategoriaProduto)) {
	            categoriaProdutoDAO.salvar(novaCategoriaProduto);
	            facesContext.addMessage(null, new FacesMessage("Categoria Cadastrada"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Já existe uma categoria com esse nome cadastrada"));
			}
			listaCategoriasProdutos = categoriaProdutoDAO.listarTodos();
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
	}
	
	public void update() throws Exception{
		try {
			if (categoriaProdutoDAO.categoriaExiste(novaCategoriaProduto)) {
	            categoriaProdutoDAO.atualizar(novaCategoriaProduto);
	            facesContext.addMessage(null, new FacesMessage("Categoria Atualizada"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Não existe categoria com esse nome cadastrada"));
			}
			listaCategoriasProdutos = categoriaProdutoDAO.listarTodos();
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
	}
	
	public void delete() throws Exception{
		try {
			if (categoriaProdutoDAO.categoriaExiste(novaCategoriaProduto)) {
	            categoriaProdutoDAO.excluir(novaCategoriaProduto);
	            novaCategoriaProduto = null;
	            facesContext.addMessage(null, new FacesMessage("Categoria Excluida"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Não existe categoria com esse nome cadastrada"));
			}
			listaCategoriasProdutos = categoriaProdutoDAO.listarTodos();
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
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
	public CategoriaProduto getNovaCategoriaProduto() {
		return novaCategoriaProduto;
	}

	public void setNovaCategoriaProduto(CategoriaProduto novaCategoriaProduto) {
		this.novaCategoriaProduto = novaCategoriaProduto;
	}

	public List<CategoriaProduto> getListaCategoriasProdutos() {
		if (listaCategoriasProdutos == null) {
			listaCategoriasProdutos = categoriaProdutoDAO.listarTodos();
		}
		return listaCategoriasProdutos;
	}

	public void setListaCategoriasProdutos(List<CategoriaProduto> listaCategoriasProdutos) {
		this.listaCategoriasProdutos = listaCategoriasProdutos;
	}
	
	
}
