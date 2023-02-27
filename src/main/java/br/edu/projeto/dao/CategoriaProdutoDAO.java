package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.CategoriaProduto;

@Stateful
public class CategoriaProdutoDAO implements Serializable{
	
	@Inject
    private EntityManager em;
	
	public CategoriaProduto encontrarCategoriaServico(String nome) {
        return em.find(CategoriaProduto.class, nome);
    }
	
	public List<CategoriaProduto> listarTodos() {
	    return em.createQuery("SELECT a FROM CategoriaProduto a ", CategoriaProduto.class).getResultList();    
	}
	
	public List<CategoriaProduto> findByName(CategoriaProduto c){
		return em.createQuery("SELECT a FROM CategoriaProduto a WHERE a.nome = '" + c.getNome() + "'", CategoriaProduto.class).getResultList();
	}
	
	public boolean categoriaExiste(CategoriaProduto c) {
		return !findByName(c).isEmpty();
	}

	public void salvar(CategoriaProduto u) {
		em.persist(u);
	}
	
	public void atualizar(CategoriaProduto u) {
		em.merge(u);
	}
	
	public void excluir(CategoriaProduto u) {
		em.remove(em.getReference(CategoriaProduto.class, u.getNome()));
	}
}
