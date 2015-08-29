package br.com.Interface;

import java.util.List;

import br.com.core.Interface.IModel;
import br.com.core.Util.Retorno;



public interface IViewModel {

	public Retorno salvar(IModel<?> imodel);
	
	public Retorno desativar(IModel<?> imodel);
	
	public List<IModel<?>> listar(IModel<?> imodel);
	
	public Retorno ativar(IModel<?> imodel);
	
}
