package br.com.ViewModel;

import br.com.Controller.CategoriaController;
import br.com.Controller.GenericController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Categoria;

@SuppressWarnings("unchecked")
public class CategoriaViewModel extends GenericViewModel {

	@Override
	public Categoria getObject() {
		return new Categoria();
	}

	@Override
	public GenericController getControl() {
		return new CategoriaController();
	}

}
