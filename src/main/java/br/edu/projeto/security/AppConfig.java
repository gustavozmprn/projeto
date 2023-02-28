package br.edu.projeto.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

//Configurações de autenticação/autorização para a API de segurança
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "login.xhtml",
                errorPage = "login-error.xhtml"
        )
)

//Configura local e forma onde usuários e papéis/permissões/roles serão consultados/autenticados
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:/PostgresDS",
        //callerQuery = "SELECT senha FROM usuario WHERE usuario = ?",
        callerQuery = "SELECT senha FROM funcionario WHERE username = ?",
        //groupsQuery = "SELECT permissao FROM tipo_permissao JOIN permissao USING (id_tipo_permissao) JOIN usuario USING (id_usuario) WHERE usuario = ?"
        groupsQuery = "SELECT descr FROM tipo_permissao JOIN permissoes on permissoes.perm_id = tipo_permissao.id JOIN funcionario on funcionario.username = permissoes.func_user WHERE funcionario.username = ?"
)

@ApplicationScoped
public class AppConfig {
	
}
