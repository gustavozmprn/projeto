package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.CategoriaServico;

@Stateful
public class CategoriaServicoDAO implements Serializable{
	
	@Inject
    private EntityManager em;
	
	public CategoriaServico encontrarCategoriaServico(String nome) {
        return em.find(CategoriaServico.class, nome);
    }
	
	public List<CategoriaServico> listarTodos() {
	    return em.createQuery("SELECT a FROM ListaTarefa a ", CategoriaServico.class).getResultList();    
	}
}