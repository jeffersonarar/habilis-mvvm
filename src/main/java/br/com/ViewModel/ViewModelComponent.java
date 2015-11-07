package br.com.ViewModel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class ViewModelComponent {

	
	@Command
	public void redirectEmpresa(){
		Executions.sendRedirect("/paginas/empresa/listar.zul");
	}
	
	@Command
	public void redirectMatriz(){
		Executions.sendRedirect("/paginas/matriz/listar.zul");
	}
	
	@Command
	public void redirectCategoria(){
		Executions.sendRedirect("/paginas/categoria/listar.zul");
	}
	
	@Command
	public void redirectEstagiario(){
		Executions.sendRedirect("/paginas/estagiario/listar.zul");
	}
	
	@Command
	public void redirectConfiguracoes(){
		Executions.sendRedirect("/paginas/configuracao/cadastrar.zul");
	}
	
	@Command
	public void redirectDisciplinas(){
		Executions.sendRedirect("/paginas/disciplina/listar.zul");
	}
	
	@Command
	public void redirectRelatorios(){
		Executions.sendRedirect("/paginas/relatorios/listarDisciplinas.zul");
	}
	
	@Command
	public void redirectConteudo(){
		Executions.sendRedirect("/paginas/conteudo/listar.zul");
	}
	
	@Command
	public void redirectAtividades(){
		Executions.sendRedirect("/paginas/atividade/listar.zul");
	}
	
	@Command
	public void redirectContrato(){
		Executions.sendRedirect("/paginas/contratoestagio/listar.zul");
	}
}

