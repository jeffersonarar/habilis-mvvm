package br.com.ViewModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import br.com.Controller.AtividadesRealizadasController;
import br.com.core.Interface.IModel;
import br.com.core.Model.Atividade;
import br.com.core.Model.AtividadesRealizadas;
import br.com.core.Model.Conteudo;
import br.com.core.Model.Disciplina;
import br.com.core.Model.RelatorioAtividades;

public class AtividadesRealizadasViewModel extends
		GenericViewModel<AtividadesRealizadas, AtividadesRealizadasController> {

	private List<?> lstAtividadesRealizadas;
	private List<?> lstAtividadesRealizadasAlunos;
	private List<?> lstEstagiarios;
	private List<?> lstConteudos;
	private List<?> lstAtividades;
	private String nomeDisciplina;
	private String nomeMatriz;
	private Disciplina selectedDisciplina;
	private Atividade selectedAtividade;
	private Conteudo selectedConteudo;
	private Window winConteudo, winAtividade, winAtividadeEstagiario,
			winConteudoEstagiario, winEstagiario;
	private Listbox listboxConteudo;

	@Init
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("EntityObject") List<?> e1,
			@ExecutionArgParam("recordMode") String recordMode)
			throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		String msn;
		setRecordMode(recordMode);
		if (lstAtividadesRealizadas == null) {
			getLstAtividadesRealizadas();
		}

		if (e1 != null) {
			if (recordMode.equals("LSTCONTEUDO")) {

				setWin((Window) view.getFellow("winConteudo"));
				this.lstConteudos = e1;
				// setEntity(e1);
			}
			if (recordMode.equals("LSTATIVIDADES")) {
				setWin((Window) view.getFellow("winAtividade"));
				this.lstAtividades = e1;
			}
			if (recordMode.equals("LSTESTAGIARIOS")) {
				setWin((Window) view.getFellow("winEstagiario"));
				this.lstEstagiarios = e1;
			}
		}

		msn = String.valueOf(Executions.getCurrent().getParameter("id"));
		if (msn != null && !msn.equalsIgnoreCase("null")) {
			// MostrarMensagem(msn);
			try {
				gerarRelatorio(Integer.parseInt(msn));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(msn);
		}
	}

	@Command
	@NotifyChange("lstAtividadesRealizadas")
	public List<?> listarDisciplinas() {
		setLstAtividadesRealizadas(getControl()
				.listarDisciplinasComMaisAssociacao(nomeDisciplina, nomeMatriz));
		return lstAtividadesRealizadas;
	}

	@Command
	public void listarConteudos() {
		getLstConteudos();
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EntityObject", this.lstConteudos);
		map.put("recordMode", "LSTCONTEUDO");
		Executions.createComponents("listarConteudo.zul", null, map);
	}

	@Command
	public void listarEstagiario() {
		getLstEstagiarios();
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EntityObject", this.lstEstagiarios);
		map.put("recordMode", "LSTESTAGIARIOS");
		Executions.createComponents("listarEstagiarios.zul", null, map);
	}

	@Command
	public void listarAtividades() {
		getLstAtividades();
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EntityObject", this.lstAtividades);
		map.put("recordMode", "LSTATIVIDADES");
		Executions.createComponents("listarAtividades.zul", null, map);
	}

	@Override
	protected void save(IModel<?> imodel) {
		// TODO Auto-generated method stub

	}

	@Override
	public AtividadesRealizadas getObject() {
		return new AtividadesRealizadas();
	}

	@Override
	public AtividadesRealizadasController getControl() {
		return new AtividadesRealizadasController();
	}

	@Command
	@NotifyChange("lstConteudos")
	public List<?> getLstConteudos() {
		if (selectedDisciplina != null) {
			setLstConteudos(getControl().listarConteudosComMaisAssociacao(
					selectedDisciplina.getId()));
		}
		return lstConteudos;
	}

	@Command
	public void gerarXLS(BindContext ctx) {
		List<?> novaLista = null;
		String param = ctx.getCommandArg("arg1").toString();
		try {
			if (param.equals("disciplinas")) {
				novaLista = lstAtividadesRealizadas;
			}
			if (param.equals("atividades")) {
				novaLista = lstAtividades;
			}
			if (param.equals("estagiarios")) {
				novaLista = lstEstagiarios;
			}
			if (param.equals("conteudos")) {
				novaLista = lstConteudos;
			}
			super.gerarRelatorioXLS(param, novaLista, param);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gerarRelatorio(long id) throws JRException, SQLException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idcontrato", id);
			setLstAtividadesRealizadasAlunos(getControl().buscarPorAtividades(
					new AtividadesRealizadas(), id));
			AtividadesRealizadas atividadesRealizadas = null;
			List<RelatorioAtividades> relatorioAtividades = new ArrayList<RelatorioAtividades>();

			for (Object object : lstAtividadesRealizadasAlunos) {
				atividadesRealizadas = (AtividadesRealizadas) object;
				RelatorioAtividades relatorioAtividades2 = new RelatorioAtividades();
				relatorioAtividades2.setId(atividadesRealizadas.getId());
				relatorioAtividades2.setCe_cpf_supervisor(atividadesRealizadas
						.getContratoEstagio().getCpf_supervisor());
				relatorioAtividades2
						.setCe_data_fim((Timestamp) atividadesRealizadas
								.getContratoEstagio().getData_fim());
				relatorioAtividades2
						.setCe_data_inicio((Timestamp) atividadesRealizadas
								.getContratoEstagio().getData_inicio());
				relatorioAtividades2
						.setCe_email_supervisor(atividadesRealizadas
								.getContratoEstagio().getEmail_supervisor());
				relatorioAtividades2.setCe_nome_supervisor(atividadesRealizadas
						.getContratoEstagio().getNome_supervisor());
				relatorioAtividades2.setCo_nome(atividadesRealizadas
						.getConteudo().getNome());
				relatorioAtividades2.setEs_matricula(atividadesRealizadas
						.getContratoEstagio().getEstagiario().getMatricula());
				relatorioAtividades2.setEs_nome(atividadesRealizadas
						.getContratoEstagio().getEstagiario().getNome());
				relatorioAtividades2.setEs_cpf(atividadesRealizadas
						.getContratoEstagio().getEstagiario().getCpf());
				relatorioAtividades.add(relatorioAtividades2);

			}

			JRPdfExporter exporter = new JRPdfExporter();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			JasperReport jr = JasperCompileManager.compileReport(Executions
					.getCurrent().getSession().getWebApp()
					.getRealPath("/relatorios/relatorio.jrxml"));
			JasperPrint jp = JasperFillManager.fillReport(jr, null,
					new JRBeanCollectionDataSource(relatorioAtividades));
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					outputStream);
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);
	
			exporter.exportReport();			
			Filedownload.save(
					new ByteArrayInputStream(outputStream.toByteArray()),
					"application/pdf", "relatorioAtividades.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

	@Command
	public void gerarPDF(BindContext ctx) {
		List<?> novaLista = null;
		String param = ctx.getCommandArg("arg1").toString();
		try {
			if (param.equals("disciplinas")) {
				novaLista = lstAtividadesRealizadas;
			}
			if (param.equals("atividades")) {
				novaLista = lstAtividades;
			}
			if (param.equals("estagiarios")) {
				novaLista = lstEstagiarios;
			}
			if (param.equals("conteudos")) {
				novaLista = lstConteudos;
			}
			super.gerarRelatorioPDF(param, novaLista, param);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange("lstAtividades")
	public List<?> getLstAtividades() {
		if (selectedDisciplina != null) {
			setLstAtividades(getControl().listarAtividadesComMaisAssociacao(
					selectedDisciplina.getId()));
		}
		return lstAtividades;
	}

	@Command
	@NotifyChange("lstEstagiarios")
	public List<?> getLstEstagiarios() {
		if (selectedAtividade != null) {
			setLstEstagiarios(getControl().listarAtividadesEstagiarios(
					selectedAtividade.getId()));
		}
		if (selectedConteudo != null) {
			setLstEstagiarios(getControl().listarEstagiariosConteudos(
					selectedConteudo.getId()));
		}
		return lstEstagiarios;
	}

	public void setLstConteudos(List<?> lstConteudos) {
		this.lstConteudos = lstConteudos;
	}

	public void setLstAtividades(List<?> lstAtividades) {
		this.lstAtividades = lstAtividades;
	}

	public Window getWinConteudo() {
		return winConteudo;
	}

	public void setWinConteudo(Window winConteudo) {
		this.winConteudo = winConteudo;
	}

	public Window getWinAtividade() {
		return winAtividade;
	}

	public void setWinAtividade(Window winAtividade) {
		this.winAtividade = winAtividade;
	}

	public Window getWinAtividadeEstagiario() {
		return winAtividadeEstagiario;
	}

	public void setWinAtividadeEstagiario(Window winAtividadeEstagiario) {
		this.winAtividadeEstagiario = winAtividadeEstagiario;
	}

	public Window getWinConteudoEstagiario() {
		return winConteudoEstagiario;
	}

	public void setWinConteudoEstagiario(Window winConteudoEstagiario) {
		this.winConteudoEstagiario = winConteudoEstagiario;
	}

	public Listbox getListboxConteudo() {
		return listboxConteudo;
	}

	public void setListboxConteudo(Listbox listboxConteudo) {
		this.listboxConteudo = listboxConteudo;
	}

	public List<?> getLstAtividadesRealizadas() {
		return lstAtividadesRealizadas;
	}

	public void setLstAtividadesRealizadas(List<?> list) {
		this.lstAtividadesRealizadas = list;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public String getNomeMatriz() {
		return nomeMatriz;
	}

	public void setNomeMatriz(String nomeMatriz) {
		this.nomeMatriz = nomeMatriz;
	}

	public Disciplina getSelectedDisciplina() {
		return selectedDisciplina;
	}

	public void setSelectedDisciplina(Disciplina selectedDisciplina) {
		this.selectedDisciplina = selectedDisciplina;
	}

	public Atividade getSelectedAtividade() {
		return selectedAtividade;
	}

	public void setSelectedAtividade(Atividade selectedAtividade) {
		this.selectedAtividade = selectedAtividade;
	}

	public Conteudo getSelectedConteudo() {
		return selectedConteudo;
	}

	public void setSelectedConteudo(Conteudo selectedConteudo) {
		this.selectedConteudo = selectedConteudo;
	}

	public void setLstEstagiarios(List<?> lstEstagiarios) {
		this.lstEstagiarios = lstEstagiarios;
	}

	public Window getWinEstagiario() {
		return winEstagiario;
	}

	public void setWinEstagiario(Window winEstagiario) {
		this.winEstagiario = winEstagiario;
	}

	public List<?> getLstAtividadesRealizadasAlunos() {
		return lstAtividadesRealizadasAlunos;
	}

	public void setLstAtividadesRealizadasAlunos(
			List<?> lstAtividadesRealizadasAlunos) {
		this.lstAtividadesRealizadasAlunos = lstAtividadesRealizadasAlunos;
	}

}
