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

import br.com.Controller.DisciplinaController;
import br.com.Controller.EstagiarioController;
import br.com.Controller.GenericController;
import br.com.Controller.MatrizController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Disciplina;
import br.com.core.Model.Estagiario;
import br.com.core.Model.Matriz;
import br.com.core.Util.Retorno;

@SuppressWarnings("unchecked")
public class DisciplinaViewModel extends GenericViewModel<Disciplina, DisciplinaController>{

	private List<?> disciplinaList;
	private Matriz matrizSelecionada;
	private List<IModel<?>> matrizList;
	private Disciplina disciplina = new Disciplina();
	
	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Disciplina d1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		if (d1 != null) {
			if (recordMode.equals("EDIT")) {

				setWin((Window) view.getFellow("idWindowAlterar"));
				this.selectedEntity = d1;
				setEntity(d1);
			}
		} else {
			getEntity();
			super.init();
		}

	}
	@Override
	public Disciplina getObject() {
		return new Disciplina();
	}

	@Override
	public DisciplinaController getControl() {
		return new DisciplinaController();
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
			setCurSelectedEntity(disciplinaList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}
	

	@Command
	@NotifyChange("disciplinaList")
	public List<IModel<?>> listar() {
		disciplinaList = super.listar(getEntity());
		return (List<IModel<?>>) disciplinaList;
	}
	

	@Override
	protected void save(IModel imodel) {
		setDisciplina(getEntity());
		getDisciplina().setMatriz(getMatrizSelecionada());
		setEntity(getDisciplina());
		
	}

	public List<?> getDisciplinaList() {
		return disciplinaList;
	}

	@Command
	@NotifyChange({"disciplinaList", "lastUpdate" })
	@GlobalCommand
	public void setDisciplinaList(List<?> disciplinaList) {
		this.disciplinaList = disciplinaList;
		lastUpdate = Calendar.getInstance().getTime();
	}
	

	@NotifyChange("matrizList")
	public List<IModel<?>> getMatrizList() {
		Matriz matriz = new Matriz();
		MatrizController matrizController = new MatrizController();
		setMatrizList(matrizController.listar(matriz));
		return matrizList;
	}

	public Matriz getMatrizSelecionada() {
		return matrizSelecionada;
	}

	public void setMatrizSelecionada(Matriz matrizSelecionada) {
		this.matrizSelecionada = matrizSelecionada;
	}

	public void setMatrizList(List<IModel<?>> matrizList) {
		this.matrizList = matrizList;
	}
	public Disciplina getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}
