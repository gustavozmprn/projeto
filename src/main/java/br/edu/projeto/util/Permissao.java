package br.edu.projeto.util;

//Classe enumeradora, responsável por criar um tipo customizado/complexo
public enum Permissao { 	
    ADMINISTRADOR(1), NORMAL(2);
	
	public int id;
	
	Permissao(int id){
		this.id = id;
	}
}
