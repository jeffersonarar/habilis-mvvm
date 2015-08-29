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

import br.com.Controller.GenericController;
import br.com.Controller.MatrizController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Empresa;
import br.com.core.Model.Matriz;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MatrizViewModel extends GenericViewModel {

	private List<?> matrizList;
	
	
	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Matriz m1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		if (m1 != null) {
			if (recordMode.equals("EDIT")) {

				setWin((Window) view.getFellow("idWindowAlterar"));
				this.selectedEntity = m1;
				setEntity(m1);
			}
		} else {
			super.init();
		}

	}
	
	@Override
	public Matriz getObject() {
		return new Matriz();
	}

	@Override
	public GenericController getControl() {
		return new MatrizController();
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
			setCurSelectedEntity(matrizList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}

	@Command
	@NotifyChange("matrizList")
	public List<IModel<?>> listar() {
		matrizList = super.listar(getEntity());
		return (List<IModel<?>>) matrizList;
	}

	public List<?> getMatrizList() {
		return matrizList;
	}

	@Command
	@NotifyChange({" matrizList;", "lastUpdate" })
	@GlobalCommand
	public void setMatrizList(List<?> matrizList) {
		this.matrizList = matrizList;
		lastUpdate = Calendar.getInstance().getTime();
	}

}
