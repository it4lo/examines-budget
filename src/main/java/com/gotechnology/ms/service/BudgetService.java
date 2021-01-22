package com.gotechnology.ms.service;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;

import com.gotechnology.ms.interfac.PriceTable;
import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.model.BudgetExam;
import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.model.TypeBudget;
import com.gotechnology.ms.repository.BudgetRepository;
import com.gotechnology.ms.repository.ExamRepository;
import com.gotechnology.ms.util.email.Email;
import com.gotechnology.ms.util.jpa.Transactional;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import util.DataUtil;
import util.ReportUtil;

public class BudgetService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BudgetRepository budgetRepo;
	@Inject
	private ExamRepository examRepo;

	public BudgetService() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	public Budget save(Budget budget) {

		if (budget.getIssueDate() == null) {
			budget.setIssueDate(new Date());
			budget.setExpirationDate(DataUtil.getDataMaisDias(new Date()));
		}

		return budgetRepo.save(budget);
	}

	@Transactional
	public void remove(Budget budget) {
		budgetRepo.remove(budget);
	}

	@Transactional
	public void atualizarLista(List<BudgetExam> tuples, TypeBudget type, Budget budget) {
		PriceTable priceTable = type.oberPriceTable();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal totalDiscount = BigDecimal.ZERO;

		for (BudgetExam tuple : tuples) {
			tuple.setPrice(tuple.getExam() != null
					? priceTable.calculatePrice(tuple.getExam()).multiply(BigDecimal.valueOf(tuple.getAmount()))
					: BigDecimal.ZERO);
			tuple.setUnitPrice(priceTable.calculatePrice(tuple.getExam()));
			total = total.add(tuple.getPrice() != null ? tuple.getPrice() : BigDecimal.ZERO);
			totalDiscount = totalDiscount.add(tuple.getDiscount() != null ? tuple.getDiscount() : BigDecimal.ZERO);
			budgetRepo.saveTuple(tuple);
		}

		BigDecimal discountGeneral = budget.getDiscountGeneral() != null ? budget.getDiscountGeneral()
				: BigDecimal.ZERO;

		budget.setPrice(total);
		budget.setDiscount(totalDiscount.add(discountGeneral));
		budget.setDiscountedPrice(budget.getPrice().subtract(budget.getDiscount()));

	}

	public void printBudget(Budget budget) {
		budget = budgetRepo.findById(budget.getId());
		Filtro f = new Filtro();
		f.setIdBudget(budget.getId());
		List<BudgetExam> tuples = getBudgetExams(f);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		String logo = path + "resources/images/logo_ms.png";
		String pointPath = path + "resources/images/push-pin.png";

		Map parametros = new HashMap();
		parametros.put("imagemLogo", logo);
		parametros.put("DATA_EMISSAO", "Manaus, "+new SimpleDateFormat("dd/MM/yyyy").format(budget.getIssueDate()));
		parametros.put("DATA_VALIDADE",   new SimpleDateFormat("dd/MM/yyyy").format(budget.getExpirationDate()));
		parametros.put("EMISSOR", budget.getEmissor());
		parametros.put("EMPRESA", budget.getEmpresa());
		parametros.put("NUMERO", budget.getNumero());
		parametros.put("TABELA", budget.getType().getName());
		parametros.put("VALOR", budget.getPrice());
		parametros.put("DESCONTO", budget.getDiscount());
		parametros.put("VALOR_COM_DESCONTO", budget.getDiscountedPrice());
		parametros.put("DIAS_VALIDADE", "Propostas válida por "+budget.getDiasValidade()+" dia(s).");
		parametros.put("CONTATO", budget.getContact());
		parametros.put("FONE_CONTATO", budget.getCellPhone());
		parametros.put("POINT_IMG", pointPath);
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(tuples,false);

		try {

			if (budget.getShowColumns()) {
				JasperDesign jd = JRXmlLoader.load(path + "resources/report/budget.jrxml");
				JasperReport report = JasperCompileManager.compileReport(jd);
				ReportUtil.openReport("OS", "" + budget.getId(), report, parametros, dataSource);
			} else {
				JasperDesign jd = JRXmlLoader.load(path + "resources/report/budget_no_collums.jrxml");
				JasperReport report = JasperCompileManager.compileReport(jd);
				ReportUtil.openReport("OS", "" + budget.getId(), report, parametros, dataSource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public BudgetExam saveTuple(BudgetExam tuple, List<BudgetExam> tuples) {

		boolean gravar = tuple.getId() == null ? true
				: !(budgetRepo.getBudgetExamByIdExame(tuple.getId(), tuple.getExam().getId()) > 0);
		if (gravar)
			setDescripObserv(tuple);

		PriceTable price = tuple.getBudget().getType().oberPriceTable();
		tuple.setPrice(price.calculatePrice(tuple.getExam()).multiply(BigDecimal.valueOf(tuple.getAmount())));
		tuple.setUnitPrice(price.calculatePrice(tuple.getExam()));

		if (tuple.getDiscount() == null)
			tuple.setDiscount(BigDecimal.ZERO);

		List<BigDecimal> values = getPrice(tuples);

		BigDecimal discountGeneral = tuple.getBudget().getDiscountGeneral() != null
				? tuple.getBudget().getDiscountGeneral()
				: BigDecimal.ZERO;

		tuple.getBudget().setPrice(values.get(0).add(tuple.getPrice()));
		tuple.getBudget().setDiscount(values.get(1).add(tuple.getDiscount()));
		tuple.getBudget().setDiscount(tuple.getBudget().getDiscount().add(discountGeneral));
		tuple.getBudget().setDiscountedPrice(tuple.getBudget().getPrice().subtract(tuple.getBudget().getDiscount()));

		return budgetRepo.saveTuple(tuple);
	}

	@Transactional
	public void removeTuple(BudgetExam tuple) {
		budgetRepo.removeTuple(tuple);
	}

	// RETORNA DOIS VALORES TOTAL E DESCONTO RESPECTIVAMENTE
	public List<BigDecimal> getPrice(List<BudgetExam> tuples) {
		List<BigDecimal> values = new ArrayList<>();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal totalDiscount = BigDecimal.ZERO;
		for (BudgetExam be : tuples) {
			total = total.add(be.getPrice() != null ? be.getPrice() : BigDecimal.ZERO);
			totalDiscount = totalDiscount.add(be.getDiscount() != null ? be.getDiscount() : BigDecimal.ZERO);
		}
		values.add(total);
		values.add(totalDiscount);
		return values;
	}

	public void setDescripObserv(BudgetExam tuple) {
		tuple.setDescription(tuple.getExam().getDescription());
		tuple.setNote(tuple.getExam().getNote() != null ? tuple.getExam().getNote() : "");
	}

	private String emails = "";

	public void prepararEmail(Email email, Budget budget) {

		email.setFromEmail("mastersaudelab@gmail.com"); // CRIAR EMAIL;
		email.setSubject("ORÇAMENTO Nº: " + budget.getId() + " Data:"
				+ new SimpleDateFormat("dd/MM/yyyy").format(budget.getIssueDate()));

		email.setSenhaEmail("s3nh@master");
		// email.setToEmail(emails);
	}

	public void sendBudget(Email email, Budget budget) {
		try {

			if (email.verificaInternet()) {
				email.sendBudget(budget);
				addMessage("", "Mensagem enviada com sucesso", FacesMessage.SEVERITY_INFO);
				email = new Email();
			}

		} catch (EmailException e) {
			addMessage("", "Problema de conexão com a internet, "
					+ "por favor contate o administrador do sistema, o seu envio não foi possível, tente mais tarde.",
					FacesMessage.SEVERITY_WARN);
		} catch (IOException e) {
			addMessage("", "Problema de conexão com a internet, "
					+ "por favor contate o administrador do sistema, o seu envio não foi possível, tente mais tarde.",
					FacesMessage.SEVERITY_WARN);
		}
	}

	public void addMessage(String summary, String detail, Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String gerarConteudoHTMLSC(String titulo, String texto) {
		StringBuilder html = new StringBuilder("<html> <head> <style>");
		html.append("table {width:100%;} table, th, td { border: 1px solid black; border-collapse: collapse;}");
		html.append("th, td { padding: 5px; text-align: left;} table#t01 tr:nth-child(even) {");
		html.append("background-color: #eee;} table#t01 tr:nth-child(odd) { background-color:#fff;}");
		html.append("table#t01 th { background-color: #849A39; color: white; }");
		html.append("</style>");
		html.append("</head>");

		html.append("<body>");

		html.append("</body> </html>");

		html.append("<p>");
		html.append(texto);
		html.append("</p>");
		return html.toString();
	}

	public List<BudgetExam> getBudgetExams(Filtro filtro) {
		return budgetRepo.getBudgetExams(filtro);
	}

	public List<Exam> getExamsByString(String s) {
		return examRepo.getExamsByString(s);
	}

	public Integer getNumeroSequencial() {
		return budgetRepo.getNumeroSequencial();
	}

	public List<Budget> getBudgets(Filtro filtro) {
		return budgetRepo.getBudgets(filtro);
	}

	public Budget findById(Long id) {
		return budgetRepo.findById(id);
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

}
