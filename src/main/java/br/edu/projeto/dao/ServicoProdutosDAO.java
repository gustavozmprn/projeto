package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.ServicoProdutos;

@Stateful
public class ServicoProdutosDAO implements Serializable {
	@Inject
    private EntityManager em;
	
	public List<ServicoProdutos> listarTodos() {
	    return em.createQuery("SELECT a FROM ServicoProdutos a ", ServicoProdutos.class).getResultList();      
	}
	
	public List<ServicoProdutos> listarTodosPorNumero(Integer i) {
	    return em.createQuery("SELECT a FROM ServicoProdutos a WHERE a.num_servico = "+i, ServicoProdutos.class).getResultList();      
	}
	
	public void salvar(ServicoProdutos s) {
		em.persist(s);
	}
	
	public void atualizar(ServicoProdutos s) {
		em.merge(s);
	}
}
