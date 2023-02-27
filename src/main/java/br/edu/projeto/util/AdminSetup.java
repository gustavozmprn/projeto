package br.edu.projeto.util;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.edu.projeto.dao.FuncionarioDAO;
import br.edu.projeto.dao.TipoPermissaoDAO;
import br.edu.projeto.model.Funcionario;
import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Usuario;

//Executa classe uma única vez ao iniciar a aplicação no servidor
//Recurso usado para criar o primeiro usuário ADMINISTRADOR no sistema, se o mesmo não existir ainda
@WebListener
public class AdminSetup implements ServletContextListener {

	@Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private FuncionarioDAO funcionarioDAO;
    
    @Inject
    private TipoPermissaoDAO tipoPermissaoDAO;

    //private Usuario admin;
    private Funcionario admin;
    
    public void contextInitialized(ServletContextEvent event) {
        if (funcionarioDAO.isFuncionarioUnico("admin")){
        	admin = new Funcionario();
        	admin.setEmail("admin@admin.com");
        	admin.setUsername("admin");
        	admin.setNome("Admin");
	        String senhaPadrao = "admin";
	        admin.setSenha(passwordHash.generate(senhaPadrao.toCharArray()));
        	admin.setCEP("12345678");
        	admin.setComplemento("admin");
        	admin.setTelefone("4998305356");
        	admin.setLogradouro("admin");
        	admin.setNumero_endrc(9999);
	        TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(Permissao.ADMINISTRADOR.id);
	        permissao.addFuncionario(admin);
	        funcionarioDAO.salvar(admin);
        }
    }
}
