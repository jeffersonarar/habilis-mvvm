package br.com.ViewModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.Controller.AtividadeController;
import br.com.Controller.CategoriaController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Atividade;
import br.com.core.Model.Categoria;



public class AtividadeViewModel extends GenericViewModel<Atividade, AtividadeController> {

	private List<?> atividadeList;
	private Categoria categoriaSelecionada;
	private List<IModel<?>> categoriaList;
	private Atividade atividade = new Atividade();
	
	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Atividade a1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		if (a1 != null) {
			if (recordMode.equals("EDIT")) {

				setWin((Window) view.getFellow("idWindowAlterar"));
				this.selectedEntity = a1;
				setEntity(a1);
			}
		} else {
			getEntity();
			super.init();
		}

	}
	
	@Command
	public void telaAlterar() {

		if (selectedEntity == null) {
			Messagebox.show("Selecione algum item para alterar!", "Error",
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("EntityObject", this.selectedEntity);
			map.put("recordMode", "EDIT");
			setCurSelectedEntity(atividadeList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}
	
	@Override
	public Atividade getObject() {
		return new Atividade();
	}

	@Override
	public AtividadeController getControl() {
		return new AtividadeController();
	}

	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("atividadeList")
	public List<IModel<?>> listar() {
		atividadeList = super.listar(getEntity());
		return (List<IModel<?>>) atividadeList;
	}
	
	@Override
	protected void save(IModel imodel) {
		setAtividade(getEntity());
		getAtividade().setCategoria(getCategoriaSelecionada());
		setAtividade(getAtividade());
		
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public List<?> getAtividadeList() {
		return atividadeList;
	}

	@Command
	@NotifyChange({"atividadeList", "lastUpdate" })
	@GlobalCommand
	public void setAtividadeList(List<?> atividadeList) {
		this.atividadeList = atividadeList;
		lastUpdate = Calendar.getInstance().getTime();
	}

	@NotifyChange("categoriaList")
	public List<IModel<?>> getCategoriaList() {
		Categoria categoria = new Categoria();
		CategoriaController categoriaController = new CategoriaController();
		setCategoriaList(categoriaController.listar(categoria));
		return categoriaList;
	}
	
	public void setCategoriaList(List<IModel<?>> categoriaList) {
		this.categoriaList = categoriaList;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	

}
