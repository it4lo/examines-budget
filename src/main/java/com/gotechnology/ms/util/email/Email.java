package com.gotechnology.ms.util.email;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.gotechnology.ms.model.Budget;
import com.gotechnology.ms.model.BudgetExam;
import com.gotechnology.ms.model.Filtro;
import com.gotechnology.ms.repository.BudgetRepository;
import com.gotechnology.ms.util.jpa.CDILocator;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import util.ReportUtil;

public class Email {

	private String toEmail;
	private String fromEmail;
	//private String solicitação;
	private String subject;
	private String cc;
	private String content;
	private String senhaEmail;
	private Message message; // = new MimeMessage(criarSessionMail());
	private Multipart partes = new MimeMultipart();

	private BudgetRepository budgetRepo = CDILocator.getBean(BudgetRepository.class);

	public Email() {
	}

	public Email(String toEmail, String fromEmail, String subject, String cc, String content) {
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
		
		this.subject = subject;
		this.cc = cc;
		this.content = content;
	}

	public Email(String toEmail, String fromEmail,  String subject) {
		this.toEmail = toEmail;
		this.fromEmail = fromEmail;
		this.subject = subject;
	}

	public void sendEmailAprovacao() throws EmailException {

		StringBuilder content = new StringBuilder(
				"Orçamentos \n\n");
		content.append(this.content);

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.addTo(toEmail);
		email.setFrom(fromEmail);
		email.setSubject(subject);
		email.setMsg(content.toString());
		email.setSSLOnConnect(true);
		email.setAuthentication("it4lo.almeida@gmail.com", "s3nh40utr4s3nh4");
		email.send();

	}

	public void EnviarEmailSimples() throws AddressException, MessagingException {
		criarMessagem();
		message.setSentDate(new Date());
		message.setContent(partes);
		Transport.send(message);
	}

	public void EnviarEmailSimplesHTML() throws AddressException, MessagingException {
		criarMessagemHTML();
		message.setSentDate(new Date());
		message.setContent(partes);
		Transport.send(message);
	}

	public void EnviarEmailAprovacaoHTML(Budget budget) throws AddressException, MessagingException {
		criarMessagemHTML();
		// message.setSentDate(new Date());
		// message.setContent(partes);
		// Transport.send(message);

		MimeBodyPart part2 = new MimeBodyPart();
		part2.setDataHandler(new DataHandler(new ByteArrayDataSource(createBudgetAttempt(budget), "application/pdf")));
		part2.setFileName("Cotação.pdf");
		partes.addBodyPart(part2);
		message.setContent(partes);
		message.setSentDate(new Date());
		Transport.send(message);
	}

	public void EnviarEmailPreCadastroProduto() throws AddressException, MessagingException {
		criarMessagemHTML();
		message.setContent(partes);
		message.setSentDate(new Date());
		Transport.send(message);
	}

	public void EnviarEmailHTML() throws AddressException, MessagingException {
		criarMessagemHTML();
		message.setContent(partes);
		message.setSentDate(new Date());
		Transport.send(message);
	}

	private Session criarSessionMail(String email, String senha) {
		Properties props = new Properties();

		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", 465);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.port", 465);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, senha);
			}
		});

		session.setDebug(true);

		return session;
	}
	
	public void sendBudgetCommonsMail() throws EmailException {
		org.apache.commons.mail.Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setStartTLSEnabled(true);
		email.setStartTLSRequired(true);
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(fromEmail, "s3nh@master"));
		email.setSSLOnConnect(true);
		email.setFrom(fromEmail);
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo(toEmail);
		email.send();
		
	}

	// TODO: Envio do mapa comparativo em anexo nas partes do e-mail

	public void sendBudget(Budget budget) throws EmailException {

		try {

			criarMessagem();
			MimeBodyPart part2 = new MimeBodyPart();
			part2.setDataHandler(new DataHandler(
					new ByteArrayDataSource(createBudgetAttempt(budget), "application/pdf")));
			part2.setFileName("orçamento.pdf");
			partes.addBodyPart(part2);
			message.setContent(partes);
			message.setSentDate(new Date());
			Transport.send(message);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// TODO: Envio de SC primeiro estágio.

	public void sendBudgetHTML(Budget budget) throws EmailException {
		try {

			criarMessagemHTML();
			MimeBodyPart part2 = new MimeBodyPart();
			part2.setDataHandler(
					new DataHandler(new ByteArrayDataSource(createBudgetAttempt(budget), "application/pdf")));
			part2.setFileName("SC" + budget.getId() + ".pdf");
			partes.addBodyPart(part2);
			message.setContent(partes);
			message.setSentDate(new Date());
			Transport.send(message);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void criarMessagem() throws AddressException, MessagingException {
		message = new MimeMessage(criarSessionMail(fromEmail, senhaEmail));
		message.setFrom(new InternetAddress(fromEmail));
		Address[] to = InternetAddress.parse(toEmail);
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSubject(subject);

		MimeBodyPart part1 = new MimeBodyPart();
		part1.setText(content);
		// part1.setContent(content, "text/html; charset=UTF-8");
		partes.addBodyPart(part1);
	}

	public void criarMessagemHTML() throws AddressException, MessagingException {
		message = new MimeMessage(criarSessionMail(fromEmail, senhaEmail));
		message.setFrom(new InternetAddress(fromEmail));
		Address[] to = InternetAddress.parse(toEmail);
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSubject(subject);

		MimeBodyPart part1 = new MimeBodyPart();
		// part1.setText(content);
		part1.setContent(content, "text/html; charset=UTF-8");
		partes.addBodyPart(part1);
	}

	public void sendEmailAprovacaoDePedido() throws EmailException {

		StringBuilder content = new StringBuilder(
				"\n\n Orçamentos \n\n ");
		content.append(this.content);

		SimpleEmail email = new SimpleEmail();

		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.addTo(toEmail);
		email.setFrom(fromEmail);
		email.setSubject(subject);
		email.setMsg(content.toString());
		email.setSSLOnConnect(true);
		email.setAuthentication("italo.almeida@fas-amazonas.org", "s3nh@fas");
		email.send();

	}
	

	public Boolean verificaInternet() throws IOException {
		InetAddress endereco = InetAddress.getByName("www.google.com");

		if (endereco.isReachable(30000)) {
			return true;
		}

		return false;
	}

	public byte[] createBudgetAttempt(Budget budget) {

		budget = budgetRepo.findById(budget.getId());
		Filtro f = new Filtro();
		f.setIdBudget(budget.getId());
		List<BudgetExam> tuples = budgetRepo.getBudgetExams(f);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String path = request.getSession().getServletContext().getRealPath("/");
		String logo = path + "resources/images/logo_ms.png";

		Map parametros = new HashMap();
		parametros.put("imagemLogo", logo);
		parametros.put("DATA_EMISSAO", new SimpleDateFormat("dd/MM/yyyy hh:mm").format(budget.getIssueDate()));
		parametros.put("DATA_VALIDADE", new SimpleDateFormat("dd/MM/yyyy").format(budget.getExpirationDate()));
		parametros.put("EMISSOR", budget.getEmissor());
		parametros.put("EMPRESA", budget.getEmpresa());
		parametros.put("NUMERO", budget.getNumero());
		parametros.put("TABELA", budget.getType().getName());
		parametros.put("VALOR", budget.getPrice());
		parametros.put("DESCONTO", budget.getDiscount());
		parametros.put("VALOR_COM_DESCONTO", budget.getDiscountedPrice());
		JRDataSource dataSource = new JRBeanCollectionDataSource(tuples, false);
		
		try {
			JasperDesign jd = JRXmlLoader.load(path + "resources/report/budget.jrxml");
			JasperReport report = JasperCompileManager.compileReport(jd);
			return ReportUtil.openReportParaAnexo("OS", "Orçamento " + budget.getNumero(), report, parametros, dataSource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getTotais(BigDecimal total) {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		return format.format(total);
		// return new DecimalFormat("###,###.###").format(total);
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Multipart getPartes() {
		return partes;
	}

	public void setPartes(Multipart partes) {
		this.partes = partes;
	}

	public String getSenhaEmail() {
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}

}
