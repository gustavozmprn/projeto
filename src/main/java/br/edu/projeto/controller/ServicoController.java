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
import br.edu.projeto.dao.ServicoDAO;
import br.edu.projeto.dao.ServicoProdutosDAO;
import br.edu.projeto.model.Produto;
import br.edu.projeto.model.Servico;
import br.edu.projeto.model.ServicoProdutos;

@ViewScoped
@Named
public class ServicoController implements Serializable{

	@Inject
	private FacesContext facesContext;
	@Inject
	private ServicoDAO servicoDAO;
	@Inject
	private ServicoProdutosDAO servicoProdutosDAO;
	@Inject
	private ProdutoDAO produtoDAO;
	private Servico novoServico;
	private List<Produto> listaProdutos;
	private Produto novoProduto;
	private Produto produtoAux;
	private Integer qtdProduto;
	private Integer codigoProduto;
	private ServicoProdutos servicoProdutos;
	private List<Servico> listaServicos;
	private List<ServicoProdutos> listaProdutosNoServico;
	private Float precoFinal;
	private Float precoProdutos;
	
	@PostConstruct
	public void inicializarServico() {
		novoServico = new Servico();
		listaServicos = servicoDAO.listarTodos();
	}
	
	public void register() throws Exception{
		try {
			precoFinal = novoServico.getPreco_mobra();
			for (Produto p: listaProdutos) {
				servicoProdutos = new ServicoProdutos();
				servicoProdutos.setCodigo_produto(p.getCodigo());
				servicoProdutos.setNum_servico(novoServico.getNum_servico());
				servicoProdutos.setPreco_produto(p.getPreco());
				precoFinal = precoFinal + (p.getPreco());
			}
			novoServico.setPreco_total(precoFinal);
            servicoDAO.salvar(novoServico);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            inicializarServico();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
	}
//FUNCOES PARA INSERIR E DELETAR PRODUTOS NO SERVIÇO
	public void inserirProduto() {
		if (produtoDAO.integerIsValid(this.codigoProduto)) {
			produtoAux = new Produto();
			produtoAux = produtoDAO.encontrarProduto(this.codigoProduto);
			if ((produtoAux.getQuantidade() - qtdProduto) >= 0) {
				if (!listaProdutos.contains(produtoAux)) {
					listaProdutos.add(produtoAux);
				}else {
					facesContext.addMessage(null, new FacesMessage("Produto já foi adicionado nesse serviço!"));
				}
			}else {
				facesContext.addMessage(null, new FacesMessage("Não há quantidade suficiente do produto no estoque"));
			}
		}else {
			facesContext.addMessage(null, new FacesMessage("Produto não existe"));
		}
	}
	
	public void removerProduto() {
		produtoAux = new Produto();
		produtoAux = produtoDAO.encontrarProduto(this.codigoProduto);
		if (listaProdutos.contains(produtoAux)) {
			listaProdutos.remove(produtoAux);
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
	public Servico getNovoServico() {
		return novoServico;
	}

	public void setNovoServico(Servico novoServico) {
		this.novoServico = novoServico;
	}

	public List<Servico> getListaServicos() {
		if (listaServicos == null) {
			listaServicos = servicoDAO.listarTodos();
		}
		return listaServicos;
	}

	public void setListaServicos(List<Servico> listaServicos) {
		this.listaServicos = listaServicos;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Integer getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Integer codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	
	
}
