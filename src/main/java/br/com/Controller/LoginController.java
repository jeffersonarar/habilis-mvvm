package br.com.Controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.Interface.AuthenticationService;
import br.com.ViewModel.ConfiguracaoViewModel;
import br.com.core.Model.Configuracao;

public class LoginController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	// wire components
	@Wire
	Textbox account;
	@Wire
	Textbox password;
	@Wire
	Label message;

	// services
	AuthenticationService authService = new ConfiguracaoViewModel();
	Session sess = Sessions.getCurrent();

	@Listen("onClick=#login; onOK=#loginWin")
	public void doLogin() {
		String nm = account.getValue();
		String pd = password.getValue();

		if (!authService.login(nm, pd)) {
			message.setValue("Dados de login estão incorretos.");
			return;
		}
		//Configuracao cre = authService.getUserCredential();

		Executions.sendRedirect("/template.zul");

	//	message.setValue("Bem vindo, " + cre.getNome());
		message.setSclass("");
	}

	@Listen("onClick=#logout;")
	public void doLogout() {
		sess.removeAttribute("userCredential");
		Executions.sendRedirect("/login.zul");
	}

}
