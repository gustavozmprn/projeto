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
import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.model.Produto;

@ViewScoped
@Named
public class ProdutoController implements Serializable{

	@Inject
	private FacesContext facesContext;
	@Inject
	private ProdutoDAO produtoDAO;
	private Produto novoProduto;
	private List<Produto> listaProdutos;
	
	@PostConstruct
	public void inicializarProduto() {
		novoProduto = new Produto();
		listaProdutos = produtoDAO.listarTodos();
	}
	
	public void cadastrarProduto() {
		this.setNovoProduto(new Produto());
	}
	
	public void atualizarProduto() {
		this.setNovoProduto(new Produto());
	}
	
	public void excluirProduto() {
		this.setNovoProduto(new Produto());
	}
	
	public void register() throws Exception{
		try {
			if (produtoValido()) {
	            produtoDAO.salvar(novoProduto);
	            facesContext.addMessage(null, new FacesMessage("Produto Atualizado"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Já existe um produto com esse código cadastrado"));
			}
			inicializarProduto();
        }
		catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            if (errorMessage.contains("violates foreign key constraint")) {
            	errorMessage = "Não existe categoria com esse nome cadastrado!";
            }
	        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
	        facesContext.addMessage(null, m);
        }
	}
	
	public void update() throws Exception{
		try {
			if (!produtoValido()) {
	            produtoDAO.atualizar(novoProduto);
	            facesContext.addMessage(null, new FacesMessage("Produto Atualizado"));
			}else {
				facesContext.addMessage(null, new FacesMessage("Não existe produto com esse código cadastrado"));
			}
			inicializarProduto();
        }
		catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            if (errorMessage.contains("violates foreign key constraint")) {
            	errorMessage = "Não existe categoria com esse nome cadastrado!";
            }
	        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
	        facesContext.addMessage(null, m);
        }
	}
	
	public void delete() throws Exception{
		if(!produtoValido()) {
				produtoDAO.excluir(novoProduto);
	            facesContext.addMessage(null, new FacesMessage("Produto Deletado"));
	            novoProduto=null;
			}else {
				facesContext.addMessage(null, new FacesMessage("Não existe produto com esse código cadastrado"));
			}
		inicializarProduto();
	}

	
	private boolean produtoValido() {
		return !produtoDAO.produtoExiste(novoProduto);
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
	public Produto getNovoProduto() {
		return novoProduto;
	}

	public void setNovoProduto(Produto novoProduto) {
		this.novoProduto = novoProduto;
	}

	public List<Produto> getListaProdutos() {
		if (listaProdutos == null) {
			listaProdutos = produtoDAO.listarTodos();
		}
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	
}
