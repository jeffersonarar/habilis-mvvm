package br.com.ViewModel;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.Controller.ContratoEstagioController;
import br.com.core.Interface.IModel;
import br.com.core.Model.ContratoEstagio;
import br.com.core.Model.Empresa;
import br.com.core.Model.Estagiario;

public class ContratoEstagioViewModel extends GenericViewModel<ContratoEstagio, ContratoEstagioController> {

	private List<?> estagiarioList;
	private List<?> empresaList;
	private Empresa empreaSelecionada;
	private Estagiario estagiarioSelecionado;
	private ContratoEstagio contratoEstagio = new ContratoEstagio();
	

	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") ContratoEstagio e1,
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
			setCurSelectedEntity(estagiarioList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}
	
	@Override
	protected void save(IModel<?> imodel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ContratoEstagio getObject() {
		return new ContratoEstagio();
	}

	@Override
	public ContratoEstagioController getControl() {
		return new ContratoEstagioController();
	}

	public ContratoEstagio getContratoEstagio() {
		return contratoEstagio;
	}

	public void setContratoEstagio(ContratoEstagio contratoEstagio) {
		this.contratoEstagio = contratoEstagio;
	}

	public List<?> getEstagiarioList() {
		return estagiarioList;
	}

	public void setEstagiarioList(List<?> estagiarioList) {
		this.estagiarioList = estagiarioList;
	}

	public List<?> getEmpresaList() {
		return empresaList;
	}

	public void setEmpresaList(List<?> empresaList) {
		this.empresaList = empresaList;
	}

	public Empresa getEmpreaSelecionada() {
		return empreaSelecionada;
	}

	public void setEmpreaSelecionada(Empresa empreaSelecionada) {
		this.empreaSelecionada = empreaSelecionada;
	}

	public Estagiario getEstagiarioSelecionado() {
		return estagiarioSelecionado;
	}

	public void setEstagiarioSelecionado(Estagiario estagiarioSelecionado) {
		this.estagiarioSelecionado = estagiarioSelecionado;
	}

}
