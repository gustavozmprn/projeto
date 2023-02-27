package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.ListaTarefa;

@Stateful
public class ListaTarefaDAO implements Serializable{
	
	@Inject
    private EntityManager em;
	
	public ListaTarefa encontrarTarefa(Integer num_tarefa) {
        return em.find(ListaTarefa.class, num_tarefa);
    }
	
	public List<ListaTarefa> listarTodos() {
	    return em.createQuery("SELECT a FROM ListaTarefa a ", ListaTarefa.class).getResultList();      
	}
	
	public List<ListaTarefa> findByName(ListaTarefa tf) {
	    return em.createQuery("SELECT a FROM ListaTarefa a WHERE a.nome = '" + tf.getNome() + "'", ListaTarefa.class).getResultList();      
	}
	
	public boolean existeNome(ListaTarefa tf) {
		return !findByName(tf).isEmpty();
	}
	
	public void salvar(ListaTarefa u) {
		em.persist(u);
	}
	
	public void atualizar(ListaTarefa u) {
		em.merge(u);
	}
	public void excluir(ListaTarefa u) {
		em.remove(em.getReference(ListaTarefa.class, u.getNum_tarefa()));
	}
}
