package br.com.ViewModel;



import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import br.com.Controller.ConfiguracaoController;
import br.com.Interface.AuthenticationService;
import br.com.core.Interface.IModel;
import br.com.core.Model.Configuracao;


public class ConfiguracaoViewModel extends GenericViewModel<Configuracao, ConfiguracaoController> implements AuthenticationService{

	private String senhaAtual;
	private String confirmarSenha;
	
	
	@Override
	protected void save(IModel<?> imodel) {

		super.salvar(getEntity());
		
	}

	@Override
	public Configuracao getObject() {
		return new Configuracao();
	}

	@Override
	public ConfiguracaoController getControl() {
		return new ConfiguracaoController();
	}

	
	

	public boolean login(String account, String password) {
		Configuracao configuracao = (Configuracao) getControl().buscarUsuarioConfiguracao(new Configuracao(), account, password);
		//simples verificação de senha
		if (configuracao == null) {
			return false;
		}
		
		Session sess = Sessions.getCurrent();
		Configuracao cre = new Configuracao(configuracao.getNome());

		sess.setAttribute("userCredential",cre);
		
		return true;
	}

	public void logout() {
		Session sess = Sessions.getCurrent();
		sess.removeAttribute("userCredential");	
	}

	public Configuracao getUserCredential() {
		Session sess = Sessions.getCurrent();
		Configuracao cre = (Configuracao) sess.getAttribute("userCredential");
		if (cre == null) {
			cre = new Configuracao();//novo usuário anônimo e define a sessão
			sess.setAttribute("userCredential",cre);
		}
		return cre;
	}
	
	
	public String getConfirmarSenha() {
		return confirmarSenha;
	}

	public void setConfirmarSenha(String confirmarSenha) {
		this.confirmarSenha = confirmarSenha;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

}
