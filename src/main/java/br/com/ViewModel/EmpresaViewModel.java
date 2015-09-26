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


import br.com.Controller.EmpresaController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Empresa;


@SuppressWarnings({ "unchecked", "rawtypes" })
public class EmpresaViewModel extends GenericViewModel {

	private List<?> empresaList;

	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Empresa e1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		if (e1 != null) {
			if (recordMode.equals("EDIT")) {

				setWin((Window) view.getFellow("idWindowAlterar"));
				this.selectedEntity = e1;
				setEntity(e1);
			}
		} else {
			super.init();
		}

	}

	@Override
	public Empresa getObject() {
		return new Empresa();
	}

	@Override
	public EmpresaController getControl() {
		return new EmpresaController();
	}

	public List<?> getEmpresaList() {
		return empresaList;
	}

	@Command
	@NotifyChange({"empresaList", "lastUpdate" })
	@GlobalCommand
	public void setEmpresaList(List<?> empresaList) {
		this.empresaList = empresaList;
		lastUpdate = Calendar.getInstance().getTime();
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
			setCurSelectedEntity(empresaList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}

	@Command
	@NotifyChange("empresaList")
	public List<IModel<?>> listar() {
		empresaList = super.listar(getEntity());
		return (List<IModel<?>>) empresaList;
	}

	@Override
	protected void save(IModel imodel) {
		// TODO Auto-generated method stub

	}



}
