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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.Controller.EstagiarioController;
import br.com.Controller.GenericController;
import br.com.Controller.MatrizController;
import br.com.core.Enum.TipoMensagem;
import br.com.core.Interface.IModel;
import br.com.core.Model.Empresa;
import br.com.core.Model.Estagiario;
import br.com.core.Model.Matriz;
import br.com.core.Util.Retorno;

@SuppressWarnings("unchecked")
public class EstagiarioViewModel extends GenericViewModel<Estagiario, EstagiarioController> {

	private List<?> estagiarioList;
	private Matriz matrizSelecionada;
	private List<IModel<?>> matrizList;
	private Estagiario estagiario = new Estagiario();
	
	
	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") Estagiario e1,
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
	

	@Command
	@NotifyChange("estagiarioList")
	public List<IModel<?>> listar() {
		estagiarioList = super.listar(getEntity());
		return (List<IModel<?>>) estagiarioList;
	}
	
	@Override
	public Estagiario getObject() {
		return new Estagiario();
	}

	@Override
	public EstagiarioController getControl() {
		return new EstagiarioController();
	}
	

	public List<?> getEstagiarioList() {
		return estagiarioList;
	}
	
	
	@Command
	public void solicitar(){
		Retorno ret = getControl().buscarSolicitacao(getEntity());
		if(ret.isValid() == true){
			msgNotificacao.mensagem(TipoMensagem.SUCESSO, "Uma nova senha foi enviada para seu email!");
		}else{
			msgNotificacao.mensagem(ret.gettipoMensagem(), ret.getMensagem());
		}
	}

	@Command
	@NotifyChange({"estagiarioList", "lastUpdate" })
	@GlobalCommand
	public void setEstagiarioList(List<?> estagiarioList) {
		this.estagiarioList = estagiarioList;
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

	public void setMatrizList(List<IModel<?>> list) {
		this.matrizList = list;
	}

	@Override
	protected void save(IModel imodel) {
		getEntity();
		setEstagiario(getEntity());
		getEstagiario().setMatriz(getMatrizSelecionada());
		setEntity(getEstagiario());
	}

	public Estagiario getEstagiario() {
		return estagiario;
	}

	public void setEstagiario(Estagiario estagiario) {
		this.estagiario = estagiario;
	}
	
	
	

}
