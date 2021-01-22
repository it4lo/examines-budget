package com.gotechnology.ms.interfac;

import java.math.BigDecimal;

import com.gotechnology.ms.model.Exam;

public interface PriceTable {
	
	public BigDecimal calculatePrice(Exam exam);

}
