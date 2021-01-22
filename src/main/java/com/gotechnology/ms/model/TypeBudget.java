package com.gotechnology.ms.model;

import com.gotechnology.ms.interfac.PriceTable;
import com.gotechnology.ms.util.price.BronzePrice;
import com.gotechnology.ms.util.price.DiamondPrice;
import com.gotechnology.ms.util.price.GoldPrice;
import com.gotechnology.ms.util.price.ParticularPrice;
import com.gotechnology.ms.util.price.SilverPrice;


public enum TypeBudget {
	
	PRICE_BRONZE("Plano Bronze") {
		@Override
		public PriceTable oberPriceTable() {
			return new BronzePrice();
		}
	},
	PRICE_SILVER("Plano Prata") {
		@Override
		public PriceTable oberPriceTable() {
			return new SilverPrice();
		}
	},
	PRICE_GOLD("Plano Ouro") {
		@Override
		public PriceTable oberPriceTable() {
			return new GoldPrice();
		}
	},
	PRICE_DIAMOND("Plano Diamante") {
		@Override
		public PriceTable oberPriceTable() {
			return new DiamondPrice();
		}
	},
	PRICE_PARTICULAR("Particular") {
		@Override
		public PriceTable oberPriceTable() {
			return new ParticularPrice();
		}
	};
	
	private String name;
	
	public abstract PriceTable oberPriceTable();
	
	private TypeBudget(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
