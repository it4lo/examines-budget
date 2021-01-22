package com.gotechnology.ms.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "budget_exam")
public class BudgetExam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Exam exam;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "note", columnDefinition = "TEXT")
	private String note;

	@ManyToOne
	private Budget budget;

	@Column
	private Integer amount;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;
	
	@Column(name = "unit_price", precision = 10, scale = 2)
	private BigDecimal unitPrice;

	@Column(name = "discount", precision = 10, scale = 2)
	private BigDecimal discount;

	@Column(name = "discount_price", precision = 10, scale = 2)
	private BigDecimal discountedPrice;

	@Column
	private Boolean showDescription;

	@Column
	private Boolean showNote;

	public BudgetExam() {

	}

	public BudgetExam(Integer amount) {
		this.amount = amount;
	}

	public BudgetExam(Long id, Exam exam, String description, String note, Budget budget, Integer amount,
			BigDecimal price, BigDecimal discount, BigDecimal discountedPrice, Boolean showDescription,
			Boolean showNote) {

		this.id = id;
		this.exam = exam;
		this.description = description;
		this.note = note;
		this.budget = budget;
		this.amount = amount;
		this.price = price;
		this.discount = discount;
		this.discountedPrice = discountedPrice;
		this.showDescription = showDescription;
		this.showNote = showNote;
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
		BudgetExam other = (BudgetExam) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public Boolean getShowDescription() {
		return showDescription;
	}

	public void setShowDescription(Boolean showDescription) {
		this.showDescription = showDescription;
	}

	public Boolean getShowNote() {
		return showNote;
	}

	public void setShowNote(Boolean showNote) {
		this.showNote = showNote;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}



}
