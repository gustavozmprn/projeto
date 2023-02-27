package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Produto;

@Stateful
public class ProdutoDAO implements Serializable{

	@Inject
    private EntityManager em;
	
	public Produto encontrarProduto(int codigo) {
		return em.find(Produto.class, codigo);
	}
	
	public Boolean isProdutoUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = cb.createQuery(Produto.class);
        Root<Produto> produto = criteria.from(Produto.class);
        criteria.select(produto);
        criteria.where(cb.like(produto.get("codigo"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	public List<Produto> listarTodos() {
	    return em.createQuery("SELECT a FROM Produto a ", Produto.class).getResultList();      
	}
	
	public List<Produto> searchByCode(Produto p){
		return em.createQuery("SELECT a FROM Produto a WHERE a.codigo = " + p.getCodigo(), Produto.class).getResultList();
	}
	
	public List<Produto> searchByInteger(Integer i){
		return em.createQuery("SELECT a FROM Produto a WHERE a.codigo = " + i, Produto.class).getResultList();
	}
	
	public boolean produtoExiste(Produto p) {
		return !searchByCode(p).isEmpty();
	}
	
	public boolean integerIsValid(Integer i) {
		return !searchByInteger(i).isEmpty();
	}
	
	public void salvar(Produto u) {
		em.persist(u);
	}
	
	public void atualizar(Produto u) {
		em.merge(u);
	}
	
	public void excluir(Produto u) {
		em.remove(em.getReference(Produto.class, u.getCodigo()));
	}
}
