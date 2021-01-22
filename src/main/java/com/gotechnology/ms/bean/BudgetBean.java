package com.gotechnology.ms.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.mail.EmailException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.model.BudgetExam;
import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.model.TypeBudget;
import com.gotechnology.ms.service.BudgetService;
import com.gotechnology.ms.util.email.Email;

import util.DataUtil;
import util.UsuarioSessao;

@ViewScoped
@Named(value = "budget_bean")
public class BudgetBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public BudgetBean() {
	}

	private @Inject UsuarioSessao usuarioSessao;

	private String enumType;

	private Filtro filtro = new Filtro();

	private @Inject BudgetService service;

	private @Inject Budget budget;

	private @Inject BudgetExam tuple;

	private Boolean saveTuple = false;

	private List<Budget> budgets = new ArrayList<>();

	private List<BudgetExam> tuples = new ArrayList<>();

	private List<Exam> exams = new ArrayList<>();

	@Inject
	private BudgetExam budgetExam;

	private TypeBudget type;

	private Email email = new Email();

	public void initList() {
		carregarBudgets();
	}

	public void initCadastro() throws IOException {
		tuples = new ArrayList<>();
		if (budget.getId() == null) {
			enumType = "PRICE_BRONZE";
			budget.setType(TypeBudget.PRICE_BRONZE);
			budget.setIssueDate(new Date());
			budget.setExpirationDate(DataUtil.getDataMaisDias(new Date()));
			// budget.setNumero(service.getNumeroSequencial());
			// budget = service.save(budget);
		} else {
			enumType = budget.getType().name();
			createTuples();
		}

		// if (!(tuples.size() > 0)) {
		// createTuple();
		// }

	}

	public void saveTuple() {
		saveTuple = true;
		budgetExam.setAmount(1);
		saveTuple(budgetExam, tuples.size());
		budgetExam = new BudgetExam();
	}

	public void createTuple() {
		tuples.add(new BudgetExam(1));
	}

	public void createTuples() {
		filtro.setIdBudget(budget.getId());
		tuples = service.getBudgetExams(filtro);
	}

	public void saveTuple(BudgetExam tuple, Integer index) {

		if (saveTuple && (tuple.getExam() != null)) {
			budget.setType(TypeBudget.valueOf(enumType));
			if (budget.getId() == null) {
				budget.setNumero(service.getNumeroSequencial());
				budget = service.save(budget);
				tuple.setBudget(budget);
			} else {
				tuple.setBudget(budget);
			}

			tuples.remove(tuple);
			tuple = service.saveTuple(tuple, tuples);
			tuples.add(index, tuple);
		}

		// atualizarLista();
	}

	public BigDecimal getUnitPrice(Exam exam) {
		return exam != null ? budget.getType().oberPriceTable().calculatePrice(exam) : BigDecimal.ZERO;
	}

	public void atualizarLista() {
		if (budget.getId() != null)
			service.atualizarLista(tuples, TypeBudget.valueOf(enumType), budget);
	}

	public void removerTuple(BudgetExam tuple) {
		tuples.remove(tuple);
		if (tuple.getId() != null)
			service.removeTuple(tuple);

		budget.setDiscount(
				budget.getDiscount().subtract(tuple.getDiscount() != null ? tuple.getDiscount() : BigDecimal.ZERO));
		budget.setPrice(budget.getPrice().subtract(tuple.getPrice() != null ? tuple.getPrice() : BigDecimal.ZERO));
		budget.setDiscountedPrice(budget.getPrice().subtract(budget.getDiscount()));

		// atualizarLista();
	}

	public void verifySave(CellEditEvent event) {
		if (event.getNewValue() != null && !event.getNewValue().equals(event.getOldValue())) {
			saveTuple = true;
		} else {
			saveTuple = false;
		}
	}

	public String save() {
		if (enumType != null && !enumType.equals(""))
			budget.setType(TypeBudget.valueOf(enumType));
		atualizarLista();
		
		if (budget.getNumero() == null) {
			budget.setNumero(service.getNumeroSequencial());
		}
		
		if (budget.getId() == null || budget.getUser() == null) {
			budget.setUser(usuarioSessao.getUsuario());
		}
		
		service.save(budget);
		
		return "orcamentos?faces-redirect=true";
	}

	public void saveOn() {
		
		if (enumType != null && !enumType.equals("")) {
			budget.setType(TypeBudget.valueOf(enumType));
		}
		
		
		if (budget.getNumero() == null) {
			budget.setNumero(service.getNumeroSequencial());
		}
		
		atualizarLista();

		if (budget.getId() == null || budget.getUser() == null) {
			budget.setUser(usuarioSessao.getUsuario());
		}

		budget = service.save(budget);
	}

	public void remove() {
		service.remove(budget);
		carregarBudgets();
	}

	public void carregarBudgets() {
		budgets = service.getBudgets(filtro);
	}

	public void filtrar() {
		carregarBudgets();
	}

	public String cancelar() {
		return "orcamentos?faces-redirect=true";
	}

	public List<Exam> completeExam(String s) {
		exams = new ArrayList<Exam>();
		exams = service.getExamsByString(s);
		return exams;
	}

	private int index;
	private String password;

	public void prepairDiscount(BudgetExam tuple, int index) {
		this.tuple = tuple;
		this.index = index;
		sendDialog("PF('dlg_password').show();");
	}

	public void verificarSenha() {
		if (password.equals("12345")) {
			sendDialog("PF('dlg_password').hide();");
			sendDialog("PF('dlg_discount').show();");
			password = "";
		} else {
			addMessage("", "Senha incorreta", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void prepairDiscount() {
		sendDialog("PF('dlg_password_general').show();");
	}

	public void verificarSenhaGeral() {
		if (password.equals("12345")) {
			sendDialog("PF('dlg_password_general').hide();");
			sendDialog("PF('dlg_discount_general').show();");
			password = "";
		} else {
			addMessage("", "Senha incorreta", FacesMessage.SEVERITY_ERROR);
		}
	}

	public void saveDiscount() {
		saveTuple = true;
		saveTuple(tuple, index);
	}

	public void printBudget(Budget budget) {
		service.printBudget(budget);
	}

	public void prepairEmail() {
		email = new Email();
		service.prepararEmail(email, budget);
	}

	public void sendBudget() throws EmailException {

		service.sendBudget(email, budget);
		prepairEmail();
		// email.sendBudgetCommonsMail();
	}

	public void sendDialog(String script) {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript(script);
	}

	public void addMessage(String summary, String detail, Severity severity) {
		FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public BudgetService getService() {
		return service;
	}

	public void setService(BudgetService service) {
		this.service = service;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public List<Budget> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}

	public TypeBudget getType() {
		return type;
	}

	public void setType(TypeBudget type) {
		this.type = type;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public List<BudgetExam> getTuples() {
		return tuples;
	}

	public void setTuples(List<BudgetExam> tuples) {
		this.tuples = tuples;
	}

	public BudgetExam getTuple() {
		return tuple;
	}

	public void setTuple(BudgetExam tuple) {
		this.tuple = tuple;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public BudgetExam getBudgetExam() {
		return budgetExam;
	}

	public void setBudgetExam(BudgetExam budgetExam) {
		this.budgetExam = budgetExam;
	}

}
