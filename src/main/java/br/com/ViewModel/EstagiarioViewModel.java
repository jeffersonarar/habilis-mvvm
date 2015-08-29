package br.com.ViewModel;

import br.com.Controller.EstagiarioController;
import br.com.Controller.GenericController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Estagiario;

@SuppressWarnings("unchecked")
public class EstagiarioViewModel extends GenericViewModel {

	@Override
	public Estagiario getObject() {
		return new Estagiario();
	}

	@Override
	public GenericController getControl() {
		return new EstagiarioController();
	}

}
