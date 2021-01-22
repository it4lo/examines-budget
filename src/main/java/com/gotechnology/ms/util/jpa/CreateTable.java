package com.gotechnology.ms.util.jpa;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.gotechnology.ms.model.Exam;

public class CreateTable {

	public static void main(String[] args) {
		gerarExames();
		System.out.println("Concluído! Banco de dados populado!");
	}

	public static void gerarExames() {

		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();

		Exam ex = new Exam("Hemograma",
				"O hemograma é um exame que identifica a contagem de plaquetas vermelhas, glóbulos vermelhos e glóbulos brancos no sangue",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("Glicose",
				"O exame de glicose é feito para analisar como o corpo absorve a glicose e se o paciente pode ter diabetes e iniciar os devidos tratamentos",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("Colesterol",
				"O exame de colesterol é usado para quantificar tanto o colesterol ruim (VLDL) quanto o colesterol bom (HDL)",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("Ureia e Creatina",
				"Esses exames têm como finalidade ver como está a saúde e o funcionamento dos rins", "",
				BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("TSH e T4",
				"Excelente exame para controlar e diagnosticar doenças na glândula tireoide. A partir dos 30 anos de idade é pedido, principalmente para as mulheres",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("Ácido úrico",
				"O exame de ácido úrico é feito para diagnosticar doenças como a gota e o aparecimento de cálculos renais",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("EAS ou Urina tipo 1",
				"Exame muito utilizado para ver a função renal do paciente. Pode auxiliar o médico a encontrar doenças e infecções urinárias",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("TGO (AST)",
				"Exame para se avaliar o fígado. Valores elevados indicam lesão das células hepáticas", "",
				BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("TGP (ALP)",
				"Exame para se avaliar o fígado. Valores elevados indicam lesão das células hepáticas", "",
				BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("PSA",
				"Proteína que se eleva em caso de câncer de próstata ou prostatites (infecção da próstata)", "",
				BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("VHS ou VS",
				"Proteína que se eleva em caso de câncer de próstata ou prostatites (infecção da próstata)", "",
				BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		ex = new Exam("(EPF) EXAME PARASITOLÓGICO DE FEZES", "Exame solicitado para investigar a presença de parasitas",
				"", BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20), BigDecimal.valueOf(25),
				BigDecimal.valueOf(30));

		manager.persist(ex);

		tx.commit();
		manager.close();

	}

}
