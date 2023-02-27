package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Cliente;

@Stateful
public class ClienteDAO implements Serializable {

	@Inject
    private EntityManager em;
	
	public Cliente encontrarCliente(String cpf) {
		return em.find(Cliente.class, cpf);
	}
	
	public Boolean isClienteUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
        Root<Cliente> cliente = criteria.from(Cliente.class);
        criteria.select(cliente);
        criteria.where(cb.like(cliente.get("cpf"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	public List<Cliente> listarTodos() {
	    return em.createQuery("SELECT a FROM Cliente a ", Cliente.class).getResultList();      
	}
	
	public List<Cliente> findByCpf(Cliente c){
		return em.createQuery("SELECT a FROM Cliente a WHERE a.cpf = '" + c.getCpf() + "'", Cliente.class).getResultList();
	}
	
	public boolean clienteExiste(List <Cliente> c) {
		if (c.isEmpty()) return false;
		return true;
	}
	
	public void salvar(Cliente u) {
		em.persist(u);
	}
	
	public void atualizar(Cliente u) {
		em.merge(u);
	}
	
	public void excluir(Cliente u) {
		em.remove(em.getReference(Cliente.class, u.getCpf()));
	}
}
