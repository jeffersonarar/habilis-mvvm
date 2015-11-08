package br.com.Interface;

import br.com.core.Model.Configuracao;

public interface AuthenticationService {
	public boolean login(String account, String password);

	public void logout();

	public Configuracao getUserCredential();
}
