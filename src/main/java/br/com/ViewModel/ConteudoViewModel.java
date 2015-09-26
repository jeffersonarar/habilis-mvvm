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

import br.com.Controller.ConteudoController;
import br.com.Controller.DisciplinaController;
import br.com.Controller.GenericController;
import br.com.Controller.MatrizController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Conteudo;
import br.com.core.Model.Disciplina;
import br.com.core.Model.Empresa;
import br.com.core.Model.Matriz;
import br.com.core.Util.Retorno;

public class ConteudoViewModel extends GenericViewModel<Conteudo, ConteudoController> {

	private List<?> conteudoList;
	private Disciplina disciplinaSelecionada;
	private List<IModel<?>> disciplinaList;
	private Matriz matrizSelecionada;
	private List<IModel<?>> matrizList;
	private Conteudo conteudo = new Conteudo();
	
	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Conteudo c1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		if (c1 != null) {
			if (recordMode.equals("EDIT")) {

				setWin((Window) view.getFellow("idWindowAlterar"));
				this.selectedEntity = c1;
				setEntity(c1);
			}
		} else {
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
			setCurSelectedEntity(conteudoList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}
	
	@Override
	public Conteudo getObject() {
		return new Conteudo();
	}

	@Override
	public ConteudoController getControl() {
		return new ConteudoController();
	}

	@Override
	protected void save(IModel imodel) {
		setConteudo(getEntity());
		getConteudo().setDisciplina(getDisciplinaSelecionada());
		setEntity(getEntity());
	}
	

	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("conteudoList")
	public List<IModel<?>> listar() {
		conteudoList = super.listar(getEntity());
		return (List<IModel<?>>) conteudoList;
	}
	

	public List<?> getConteudoList() {
		return conteudoList;
	}

	
	@Command
	@NotifyChange({"conteudoList", "lastUpdate" })
	@GlobalCommand
	public void setConteudoList(List<?> conteudoList) {
		this.conteudoList = conteudoList;
		lastUpdate = Calendar.getInstance().getTime();
	}

	public Disciplina getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}

	public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}

	
	@NotifyChange("matrizList")
	public List<IModel<?>> getMatrizList() {
		Matriz matriz = new Matriz();
		MatrizController matrizController = new MatrizController();
		setMatrizList(matrizController.listar(matriz));
		return matrizList;
	}
	
	@Command
	@SuppressWarnings("unchecked")
	@NotifyChange("disciplinaList")
	public List<IModel<?>> getDisciplinaList() {
		if(getMatrizSelecionada()!=null){
			Disciplina disciplina = new Disciplina();
			DisciplinaController disciplinaController = new DisciplinaController();
			setDisciplinaList(disciplinaController.buscarForeign(disciplina, getMatrizSelecionada()));	
		}
	
		return disciplinaList;
	}

	public void setDisciplinaList(List<IModel<?>> disciplinaList) {
		this.disciplinaList = disciplinaList;
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

	public Conteudo getConteudo() {
		return conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}

	
	

}
