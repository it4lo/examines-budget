package com.gotechnology.ms.util.create;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.gotechnology.ms.model.Exam;
import com.gotechnology.ms.util.jpa.JpaUtil;

public class GeradorDeTabelasISO {

	public static void main(String[] args) {

		EntityManager manager = JpaUtil.getEntityManager();
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		criarExame(manager);
		// criarComunidade(manager);

		System.out.println("Banco populado!!!");

		tx.commit();
		manager.close();
	}

	public static void criarExame(EntityManager manager) {
		try {

			FileInputStream stream = new FileInputStream("C:/DEV/exam.csv");
			// FileInputStream stream = new
			// FileInputStream("/home/developer/Documentos/doc/bd/produtos.csv");
			InputStreamReader reader = new InputStreamReader(stream);

			BufferedReader br = new BufferedReader(reader);

			String code = "";
			String nome = "";
			String descricao = "";
			BigDecimal vlDiamante = BigDecimal.ZERO;
			BigDecimal vlOuro = BigDecimal.ZERO;
			BigDecimal vlPrata = BigDecimal.ZERO;
			BigDecimal vlBronze = BigDecimal.ZERO;
			BigDecimal vlParticular = BigDecimal.ZERO;
			//int count = 1;

			while (br.ready()) {
				String linha[] = br.readLine().split(";");
				manager.persist(new Exam(linha));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// code = linha[0] != null ? linha[0] : "";
	// nome = linha[1] != null ? linha[1] : "";
	// descricao = linha[2] != null ? linha[2] : "";
	// vlDiamante = new BigDecimal((linha[3] != null && !linha[3].equals(""))?
	// linha[3] : "0");
	// vlOuro = new BigDecimal((linha[4] != null && !linha[4].equals("")) ? linha[4]
	// : "0");
	// vlPrata = new BigDecimal((linha[5] != null && !linha[5].equals("")) ?
	// linha[5] : "0");
	// vlBronze = new BigDecimal((linha[6] != null && !linha[6].equals("")) ?
	// linha[6] : "0");
	// vlParticular = new BigDecimal((linha[7] != null && !linha[7].equals(""))?
	// linha[7] : "0");
}
