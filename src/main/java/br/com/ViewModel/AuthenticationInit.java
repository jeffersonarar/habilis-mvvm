package br.com.ViewModel;

import java.util.Map;





import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import br.com.core.Model.Configuracao;


public class AuthenticationInit implements Initiator {

	//services
	ConfiguracaoViewModel authService = new ConfiguracaoViewModel();	
	public void doInit(Page page, Map<String, Object> args) throws Exception {
		
		Configuracao cre = authService.getUserCredential();
		if (cre == null || cre.getNome() == "" || cre.getNome() == null) {
			Executions.sendRedirect("/login.zul");
			return;
		}
	}
}