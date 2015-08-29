package br.com.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Temporal;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import br.com.Controller.GenericController;
import br.com.Interface.IViewModel;
import br.com.Util.MensagemNotificacao;
import br.com.Util.Tempo;
import br.com.core.Interface.IModel;
import br.com.core.Model.Empresa;
import br.com.core.Util.Retorno;

public abstract class GenericViewModel<M extends IModel<?>, C extends GenericController<M>>
		implements IViewModel {

	private M entity;
	protected M selectedEntity;
	protected Integer curSelectedEntity;
	private C cController;
	protected String parametro;
	protected boolean ativos = true;
	protected Date lastUpdate;
	private Window win;
	private String recordMode;
	private List<?> entityList;

	@Init
	public void init() {
		setEntity(getObject());

	}

	public abstract M getObject();

	public abstract C getControl();

	MensagemNotificacao msgNotificacao = new MensagemNotificacao();

	@Command
	public void novo() {
		String nomeClasse = getEntity().getNameClass().toLowerCase();
		Executions.sendRedirect("/paginas/" + nomeClasse + "/cadastrar.zul");
	}

	@Command
	public Retorno salvar(IModel<?> imodel) {
		Retorno ret;
		if (getSelectedEntity() != null) {
			ret = getControl().alterar(getSelectedEntity());
			if(ret.isValid() != true){
				msgNotificacao.mensagem(ret.gettipoMensagem(), ret.getMensagem());
			}else{
				msgNotificacao.mensagem(ret.gettipoMensagem(), ret.getMensagem());
				fecharWindow();
			}
			
			
	
		} else {
			ret = getControl().salvar(getEntity());
			msgNotificacao.mensagem(ret.gettipoMensagem(), ret.getMensagem());
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Command
	public Retorno desativar(IModel<?> imodel) {

		Retorno ret = new Retorno(true);
		if (selectedEntity == null) {
			Messagebox.show("Selecione um item para ser deletado!", "Error",
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else if(selectedEntity.getAtivo() == false){
			Messagebox.show("Item já estava desativo!", "Aviso",
					Messagebox.OK, Messagebox.EXCLAMATION);
		} else {
			String str = "Deseja desativar "+ getEntity().toString()+" ?";
			Messagebox.show(str, "Confirm", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							Retorno ret = new Retorno(true);
							if (event.getName().equals("onNo")) {
								return;
							} else {
								if (event.getName().equals("onYes")) {

									ret = getControl().desativar(
											getSelectedEntity());
									msgNotificacao.mensagem(
											ret.gettipoMensagem(),
											ret.getMensagem());
									setSelectedEntity(null);

								}
							}
						}
					});

		}
		return ret;
	}

	public Retorno ativar(IModel<?> imodel) {
		return getControl().ativar(imodel);
	}

	public List<IModel<?>> listar(IModel<?> imodel) {

		if (ativos != true || parametro != null) {
			return getControl().listarCriterio(getEntity(), parametro, ativos);
		} else {
			return getControl().listar(getEntity());
		}

	}
	
	@Command
	public void fecharWindow() {
		String nomeClasse = getEntity().getNameClass().toLowerCase();
		if (win == null) {
			Executions.sendRedirect("/paginas/" + nomeClasse + "/listar.zul");
		} else {
			win.detach();
		}
	}

	public M getEntity() {
		return entity;
	}

	public void setEntity(M entity) {
		this.entity = entity;
	}

	public M getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(M selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public C getcController() {
		return cController;
	}

	public void setcController(C cController) {
		this.cController = cController;
	}

	public MensagemNotificacao getMsgNotificacao() {
		return msgNotificacao;
	}

	public void setMsgNotificacao(MensagemNotificacao msgNotificacao) {
		this.msgNotificacao = msgNotificacao;
	}

	public Integer getCurSelectedEntity() {
		return curSelectedEntity;
	}

	public void setCurSelectedEntity(Integer i) {
		this.curSelectedEntity = i;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public boolean isAtivos() {
		return ativos;
	}

	public void setAtivos(boolean ativos) {
		this.ativos = ativos;
	}
	

	public Window getWin() {
		return win;
	}

	public void setWin(Window win) {
		this.win = win;
	}

	public String getRecordMode() {
		return recordMode;
	}

	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}

	public List<?> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<?> entityList) {
		this.entityList = entityList;
	}

}
