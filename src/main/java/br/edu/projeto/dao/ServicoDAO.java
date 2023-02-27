package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Servico;

@Stateful
public class ServicoDAO implements Serializable{

	@Inject
    private EntityManager em;
	
	public Servico encontrarServico(int num_servico) {
		return em.find(Servico.class, num_servico);
	}
	
	public Boolean isServicoUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Servico> criteria = cb.createQuery(Servico.class);
        Root<Servico> servico = criteria.from(Servico.class);
        criteria.select(servico);
        criteria.where(cb.like(servico.get("num_servico"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	public List<Servico> listarTodos() {
	    return em.createQuery("SELECT a FROM Servico a ", Servico.class).getResultList();      
	}
	
	public List<Servico> findByNumber(Servico s){
		return em.createQuery("SELECT a FROM Servico a WHERE a.num_servico = " + s.getNum_servico(), Servico.class).getResultList(); 
	}
	
	public boolean servicoExiste(Servico s) {
		return !findByNumber(s).isEmpty();
	}
	
	public void salvar(Servico u) {
		em.persist(u);
	}
	
	public void atualizar(Servico u) {
		em.merge(u);
	}
	
	public void excluir(Servico u) {
		em.remove(em.getReference(Servico.class, u.getNum_servico()));
	}
}
