package com.gotechnology.ms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "budget")
public class Budget implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Integer numero;
	
	@Column
	private Integer diasValidade;
	
	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;
	
	@Column(name = "discount_general", precision = 10, scale = 2)
	private BigDecimal discountGeneral;
	
	@Column(name = "discounted_price", precision = 10, scale = 2)
	private BigDecimal discountedPrice;
	;
	@Column(name = "issue_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueDate;
	
	@Column(name = "expiration_date")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@Column(name = "expiration_day")
	private Integer expirationDay;
	
	@ManyToOne
	private User user;
	
	@Column
	private String empresa;
	
	@Column
	private String contact; //VULGO AC
	
	@Column
	private String cellPhone; //VULGO AC
	
	@Column
	private String emissor;
	
	@Enumerated(EnumType.STRING)
	private TypeBudget type;
	
	@Column(name = "show_columns")
	private Boolean showColumns;
	
	
	public Budget() {
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getNumero() {
		return numero;
	}


	public void setNumero(Integer numero) {
		this.numero = numero;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public BigDecimal getDiscount() {
		return discount;
	}


	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}


	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}


	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}


	public Date getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}


	public Date getExpirationDate() {
		return expirationDate;
	}


	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	public Integer getExpirationDay() {
		return expirationDay;
	}


	public void setExpirationDay(Integer expirationDay) {
		this.expirationDay = expirationDay;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public TypeBudget getType() {
		return type;
	}


	public void setType(TypeBudget type) {
		this.type = type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Budget other = (Budget) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	public String getEmpresa() {
		return empresa;
	}


	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}


	public String getEmissor() {
		return emissor;
	}


	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}


	public Boolean getShowColumns() {
		return showColumns;
	}


	public void setShowColumns(Boolean showColumns) {
		this.showColumns = showColumns;
	}


	public BigDecimal getDiscountGeneral() {
		return discountGeneral;
	}


	public void setDiscountGeneral(BigDecimal discountGeneral) {
		this.discountGeneral = discountGeneral;
	}


	public Integer getDiasValidade() {
		return diasValidade;
	}


	public void setDiasValidade(Integer diasValidade) {
		this.diasValidade = diasValidade;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getCellPhone() {
		return cellPhone;
	}


	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	
	
}
