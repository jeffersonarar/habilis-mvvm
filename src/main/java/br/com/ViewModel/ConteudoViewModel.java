package br.com.ViewModel;

import br.com.Controller.ConteudoController;
import br.com.Controller.GenericController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Conteudo;

public class ConteudoViewModel extends GenericViewModel {

	@Override
	public Conteudo getObject() {
		return new Conteudo();
	}

	@Override
	public GenericController getControl() {
		return new ConteudoController();
	}

}
