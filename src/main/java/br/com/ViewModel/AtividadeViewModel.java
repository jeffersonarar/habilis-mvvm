package br.com.ViewModel;

import br.com.Controller.AtividadeController;
import br.com.Controller.GenericController;
import br.com.core.DAO.Atividade;
import br.com.core.Interface.IModel;

public class AtividadeViewModel extends GenericViewModel {

	@Override
	public IModel getObject() {
		return (IModel) new Atividade();
	}

	@Override
	public GenericController getControl() {
		return new AtividadeController();
	}

}
