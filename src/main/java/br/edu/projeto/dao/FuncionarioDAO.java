package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Funcionario;

@Stateful

public class FuncionarioDAO implements Serializable{
	
	@Inject
    private EntityManager em;
	
	public Funcionario encontrarUser(String user) {
		return em.find(Funcionario.class, user);
	}
	
	public Boolean isFuncionarioUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Funcionario> criteria = cb.createQuery(Funcionario.class);
        Root<Funcionario> funcionario = criteria.from(Funcionario.class);
        criteria.select(funcionario);
        criteria.where(cb.like(funcionario.get("username"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	public List<Funcionario> listarTodos() {
	    return em.createQuery("SELECT a FROM Funcionario a ", Funcionario.class).getResultList();      
	}
	
	public List<Funcionario> findByUsername(Funcionario f){
		return em.createQuery("SELECT a FROM Funcionario a WHERE a.username = '" + f.getUsername() + "'", Funcionario.class).getResultList();
	}
	public boolean funcionarioExiste(List <Funcionario> f) {
		if (f.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void salvar(Funcionario u) {
		em.persist(u);
	}
	
	public void atualizar(Funcionario u) {
		em.merge(u);
	}
	
	public void excluir(Funcionario u) {
		em.remove(em.getReference(Funcionario.class, u.getUsername()));
	}
	
}
