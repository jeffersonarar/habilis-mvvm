package br.com.ViewModel;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.Controller.ContratoEstagioController;
import br.com.Controller.EmpresaController;
import br.com.Controller.EstagiarioController;
import br.com.core.Interface.IModel;
import br.com.core.Model.ContratoEstagio;
import br.com.core.Model.Empresa;
import br.com.core.Model.Estagiario;
import br.com.core.Util.Retorno;

public class ContratoEstagioViewModel extends
		GenericViewModel<ContratoEstagio, ContratoEstagioController> {

	private List<?> estagiarioList;
	private List<?> empresaList;
	private Empresa empresaSelecionada;
	private Estagiario estagiarioSelecionado;
	private ContratoEstagio contratoEstagio = new ContratoEstagio();
	private List<?> contratoList;
	private Window windowEstagiario;
	private Window windowEmpresa;
	private Textbox nomeEstagiario, nomeEmpresa;
	private boolean estagiarioHasSelected = false, empresaHasSelected = false;

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
	public void novo() {
		Executions.sendRedirect("/paginas/contratoestagio/frmContrato.zul");
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
			setCurSelectedEntity(contratoList.indexOf(selectedEntity));
			Executions.createComponents("alterar.zul", null, map);
		}

	}

	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("contratoList")
	public List<IModel<?>> listar() {
		contratoList = super.listar(getEntity());
		return (List<IModel<?>>) contratoList;
	}

	@Command
	public void close(BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
		if (getWindowEstagiario() != null) {
			setWindowEstagiario((Window) view.getFellow("windowEstagiario"));
			getWindowEstagiario().setVisible(false);
			setWindowEstagiario(null);
			setEstagiarioList(null);

		} else if (getWindowEmpresa() != null) {
			setWindowEmpresa((Window) view.getFellow("windowEmpresa"));
			getWindowEmpresa().setVisible(false);
			setWindowEmpresa(null);
			setEmpresaList(null);
		}
	}

	@Command
	public void confirmar(BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) {

		Selectors.wireEventListeners(view, this);

		if (getEstagiarioSelecionado() == null	&& getEmpresaSelecionada() == null) {

			Messagebox.show("Selecione algum item !", "Error", Messagebox.OK, Messagebox.EXCLAMATION);
			
		} else if (getEstagiarioSelecionado() != null && isEstagiarioHasSelected() == false) {
			setWindowEstagiario((Window) view.getFellow("windowEstagiario"));
			setNomeEstagiario((Textbox) view.getFellow("nomeEstagiario"));
			getNomeEstagiario().setValue(getEstagiarioSelecionado().getNome());
			getWindowEstagiario().setVisible(false);
			setEstagiarioHasSelected(true);

		} else if (getEstagiarioSelecionado() == null && isEmpresaHasSelected() == false) {
			
			setWindowEmpresa((Window) view.getFellow("windowEmpresa"));
			setNomeEmpresa((Textbox) view.getFellow("nomeEmpresa"));
			getNomeEmpresa().setValue(getEmpresaSelecionada().getNome());
			getWindowEmpresa().setVisible(false);
			setEmpresaHasSelected(true);
			
		} else if (getEstagiarioSelecionado() != null && isEmpresaHasSelected() == true) {
			
			setWindowEstagiario((Window) view.getFellow("windowEstagiario"));
			setNomeEstagiario((Textbox) view.getFellow("nomeEstagiario"));
			getNomeEstagiario().setValue(getEstagiarioSelecionado().getNome());
			getWindowEstagiario().setVisible(false);
			setEstagiarioHasSelected(true);
			
		} else if (getEstagiarioSelecionado() != null && isEmpresaHasSelected() == false) {
			
			setWindowEmpresa((Window) view.getFellow("windowEmpresa"));
			setNomeEmpresa((Textbox) view.getFellow("nomeEmpresa"));
			getNomeEmpresa().setValue(getEmpresaSelecionada().getNome());
			getWindowEmpresa().setVisible(false);
			setEmpresaHasSelected(true);
		}
	}

	@Command
	@NotifyChange("estagiarioList")
	public List<?> getEstagiarioList() {
		Estagiario estagiario = new Estagiario();
		EstagiarioController estagiarioController = new EstagiarioController();
		if (parametro == null || parametro.equals("")) {
			parametro = "";
		} else {
			setEstagiarioList(estagiarioController.listarCriterio(estagiario,
					parametro, true));
		}
		return estagiarioList;
	}

	@Command
	@NotifyChange("empresaList")
	public List<?> getEmpresaList() {
		Empresa empresa = new Empresa();
		EmpresaController empresaController = new EmpresaController();
		if (parametro == null || parametro.equals("")) {
			parametro = "";
		} else {
			setEmpresaList(empresaController.listarCriterio(empresa, parametro,
					true));
		}

		return empresaList;
	}

	@Command
	public void buscarEstagiario(BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
		setWindowEstagiario((Window) view.getFellow("windowEstagiario"));
		getWindowEstagiario().setVisible(true);
		getWindowEstagiario().doModal();
	}

	@Command
	public void buscarEmpresa(BindContext ctx,
			@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireEventListeners(view, this);
		setWindowEmpresa((Window) view.getFellow("windowEmpresa"));
		getWindowEmpresa().setVisible(true);
		getWindowEmpresa().doModal();
	}

	@Override
	protected void save(IModel<?> imodel) {
		if(getSelectedEntity() != null){
			
		}else{
			getEntity().setEstagiario(getEstagiarioSelecionado());
			getEntity().setEmpresa(getEmpresaSelecionada());
		}
		
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

	public void setEstagiarioList(List<?> estagiarioList) {
		this.estagiarioList = estagiarioList;
	}

	public void setEmpresaList(List<?> empresaList) {
		this.empresaList = empresaList;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public Estagiario getEstagiarioSelecionado() {
		return estagiarioSelecionado;
	}

	public void setEstagiarioSelecionado(Estagiario estagiarioSelecionado) {
		this.estagiarioSelecionado = estagiarioSelecionado;
	}

	public List<?> getContratoList() {
		return contratoList;
	}

	public void setContratoList(List<?> contratoList) {
		this.contratoList = contratoList;
	}

	public Window getWindowEstagiario() {
		return windowEstagiario;
	}

	public void setWindowEstagiario(Window windowEstagiario) {
		this.windowEstagiario = windowEstagiario;
	}

	public Window getWindowEmpresa() {
		return windowEmpresa;
	}

	public void setWindowEmpresa(Window windowEmpresa) {
		this.windowEmpresa = windowEmpresa;
	}

	public Textbox getNomeEstagiario() {
		return nomeEstagiario;
	}

	public void setNomeEstagiario(Textbox nomeEstagiario) {
		this.nomeEstagiario = nomeEstagiario;
	}

	public Textbox getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(Textbox nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public boolean isEstagiarioHasSelected() {
		return estagiarioHasSelected;
	}

	public void setEstagiarioHasSelected(boolean estagiarioHasSelected) {
		this.estagiarioHasSelected = estagiarioHasSelected;
	}

	public boolean isEmpresaHasSelected() {
		return empresaHasSelected;
	}

	public void setEmpresaHasSelected(boolean empresaHasSelected) {
		this.empresaHasSelected = empresaHasSelected;
	}

}
