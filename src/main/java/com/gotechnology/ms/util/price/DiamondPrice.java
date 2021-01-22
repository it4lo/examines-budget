package com.gotechnology.ms.util.price;

import java.math.BigDecimal;

import com.gotechnology.ms.interfac.PriceTable;
import com.gotechnology.ms.model.Exam;

public class DiamondPrice implements PriceTable{

	@Override
	public BigDecimal calculatePrice(Exam exam) {
		return exam.getDiamondValue();
	}



}
