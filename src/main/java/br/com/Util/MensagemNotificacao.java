package br.com.Util;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import br.com.core.Enum.TipoMensagem;

public class MensagemNotificacao {

		
		public void mensagemSucesso(String mensagem){
			Clients.showNotification(mensagem, "info", null, "middle_right", 3000);
		}
		
		public void mensagemAviso(String mensagem){
		
			Clients.showNotification(mensagem, "warning", null, "middle_right", 3000);
		}
		
		public void mensagemConfirma(String mensagem){
			 Messagebox.show(mensagem, "Confirmação", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
		}
		
		public void mensagemError(String mensagem){
			Clients.showNotification(mensagem, "error", null, "middle_right", 3000);
		}
		
		public void mensagem(TipoMensagem tipoMensagem, String msg){
			if(tipoMensagem == TipoMensagem.AVISO){
				this.mensagemAviso(msg);
			} else if(tipoMensagem == TipoMensagem.CONFIRMA){
				this.mensagemConfirma(msg);
			} else if(tipoMensagem == TipoMensagem.ERROR){
				this.mensagemError(msg);
			} else if(tipoMensagem == TipoMensagem.SUCESSO){
				this.mensagemSucesso(msg);
			}
		}
			
		

}
