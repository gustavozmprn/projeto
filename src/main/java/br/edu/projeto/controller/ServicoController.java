package br.edu.projeto.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.ProdutoDAO;
import br.edu.projeto.dao.ServicoDAO;
import br.edu.projeto.dao.ServicoProdutosDAO;
import br.edu.projeto.model.Produto;
import br.edu.projeto.model.Servico;
import br.edu.projeto.model.ServicoProdutos;
import br.edu.projeto.util.produtosQtd;

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
	private Integer qtdProdutoRemovida;
	private Integer codigoProduto;
	private ServicoProdutos servicoProdutos;
	private List<Servico> listaServicos;
	private List<ServicoProdutos> listaProdutosNoServico;
	private List<ServicoProdutos> listaProdutosNoServicoAux;
	private produtosQtd aux;
	private List<produtosQtd> listaAuxiliar;
	
	@PostConstruct
	public void inicializarServico() {
    	if (!this.facesContext.getExternalContext().isUserInRole("ADMINISTRADOR") && !this.facesContext.getExternalContext().isUserInRole("NORMAL")) {
    		try {
				this.facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
		novoServico = new Servico();
		listaServicos = servicoDAO.listarTodos();
		listaProdutosNoServico = servicoProdutosDAO.listarTodos();
		listaProdutosNoServicoAux = new ArrayList<ServicoProdutos>();
	}
	
	public void cadastrarServico() {
		this.setNovoServico(new Servico());
		this.listaProdutos = new ArrayList<Produto>();
		this.listaAuxiliar = new ArrayList<produtosQtd>();
	}
	public void register() throws Exception{
		try {
			if (servicoValido()) {
				servicoDAO.salvar(novoServico);
				for (int i=0;i<listaProdutos.size();i++) {
					servicoProdutos = new ServicoProdutos();
					servicoProdutos.setCodigo_produto(listaProdutos.get(i).getCodigo());
					servicoProdutos.setNum_servico(novoServico.getNum_servico());
					servicoProdutos.setPreco_produto(listaProdutos.get(i).getPreco());
					for (int j=0;j<listaAuxiliar.size();j++) {
						if (listaAuxiliar.get(j).getCodigo() == listaProdutos.get(i).getCodigo()) {
							qtdProdutoRemovida = listaAuxiliar.get(j).getQuantidadeRemovida();
						}
					}
					servicoProdutos.setQtd_produto(qtdProdutoRemovida);
					novoProduto = listaProdutos.get(i);
					novoProduto.setQuantidade((novoProduto.getQuantidade() - qtdProdutoRemovida));
					produtoDAO.atualizar(novoProduto);
					servicoProdutosDAO.salvar(servicoProdutos);
				}
	//			for (Produto p: listaProdutos) {
	//				servicoProdutos = new ServicoProdutos();
	//				servicoProdutos.setCodigo_produto(p.getCodigo());
	//				servicoProdutos.setNum_servico(novoServico.getNum_servico());
	//				servicoProdutos.setPreco_produto(p.getPreco());
	//			}
	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
	            facesContext.addMessage(null, m);
	            listaServicos = servicoDAO.listarTodos();
			    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
			}else {
	            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Serviço com esse numero ja existe!", "Registration successful");
	            facesContext.addMessage(null, m);
			}
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
	}
	
	public void delete() {
		if (!servicoValido()) {
			servicoDAO.excluir(novoServico);
			facesContext.addMessage(null, new FacesMessage("Produto Deletado"));
			novoServico = null;
			listaServicos = servicoDAO.listarTodos();
		    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
		    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
		}
	}
	public boolean servicoValido() {
		return !servicoDAO.servicoExiste(novoServico);
	}
//FUNCOES PARA INSERIR E DELETAR PRODUTOS NO SERVIÇO
	public void inserirProduto() {
		System.out.println("ESSE EH O CODIGO PRODUTO: "+codigoProduto);
		if (produtoDAO.integerIsValid(this.codigoProduto)) {
			aux = new produtosQtd();
			produtoAux = new Produto();
			produtoAux = produtoDAO.encontrarProduto(this.codigoProduto);
			if ((produtoAux.getQuantidade() - qtdProduto) >= 0) {
				if (listaProdutos.isEmpty() || listaProdutos.size() == 0) {
					listaProdutos.add(produtoAux);
					aux.setCodigo(produtoAux.getCodigo());
					aux.setQuantidadeRemovida(qtdProduto);
					listaAuxiliar.add(aux);
				}else {
					for (int i=0;i<listaProdutos.size();i++) {
						if (listaProdutos.get(i).getCodigo() == this.codigoProduto) {
							facesContext.addMessage(null, new FacesMessage("Produto já foi adicionado nesse serviço!"));
							PrimeFaces.current().ajax().update("form:messages");
							return;
						}
					}
					aux.setCodigo(produtoAux.getCodigo());
					aux.setQuantidadeRemovida(qtdProduto);
					listaAuxiliar.add(aux);
					listaProdutos.add(produtoAux);
					PrimeFaces.current().ajax().update("form:pt-servicos");
				}
			}else {
				facesContext.addMessage(null, new FacesMessage("Não há quantidade suficiente do produto no estoque"));
				PrimeFaces.current().ajax().update("form:messages");
			}
		}else {
			facesContext.addMessage(null, new FacesMessage("Produto não existe"));
			PrimeFaces.current().ajax().update("form:messages");
		}
}
	public void removerProduto() {
		produtoAux = new Produto();
		produtoAux = produtoDAO.encontrarProduto(this.codigoProduto);
		if (listaProdutos.contains(produtoAux)) {
			listaProdutos.remove(produtoAux);
		}
	}
	
	public void criarTabela() {
		listaProdutosNoServicoAux.clear();
		//listaProdutosNoServico = servicoProdutosDAO.listarTodosPorNumero(novoServico.getNum_servico());
		for (int i=0;i<listaProdutosNoServico.size();i++) {
			System.out.println(listaProdutosNoServico.get(i).getCodigo_produto() + " " + listaProdutosNoServico.get(i).getNum_servico());
			if (listaProdutosNoServico.get(i).getNum_servico() == novoServico.getNum_servico()) {
				listaProdutosNoServicoAux.add(listaProdutosNoServico.get(i));
			}
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

	public Integer getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(Integer qtdProduto) {
		this.qtdProduto = qtdProduto;
	}

	public List<ServicoProdutos> getListaProdutosNoServico() {
		return listaProdutosNoServico;
	}

	public void setListaProdutosNoServico(List<ServicoProdutos> listaProdutosNoServico) {
		this.listaProdutosNoServico = listaProdutosNoServico;
	}

	public List<ServicoProdutos> getListaProdutosNoServicoAux() {
		return listaProdutosNoServicoAux;
	}

	public void setListaProdutosNoServicoAux(List<ServicoProdutos> listaProdutosNoServicoAux) {
		this.listaProdutosNoServicoAux = listaProdutosNoServicoAux;
	}
	
	
	
}
