package br.com.ViewModel;

import br.com.Controller.DisciplinaController;
import br.com.Controller.GenericController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Disciplina;

@SuppressWarnings("unchecked")
public class DisciplinaViewModel extends GenericViewModel {

	@Override
	public Disciplina getObject() {
		return new Disciplina();
	}

	@Override
	public GenericController getControl() {
		return new DisciplinaController();
	}

}
