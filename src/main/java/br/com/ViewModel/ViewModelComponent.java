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
}

