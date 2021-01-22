package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {
	
	public static Date verificaDiaUtil(Calendar calendar) {
		int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		
		while((diaSemana >= 6 || diaSemana <= 2)){
		 //System.out.println(diaSemana);	
		 calendar.add(Calendar.DAY_OF_YEAR, 1);	 
		 diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
		}	
		
		if(diaSemana == 4) calendar.add(Calendar.DAY_OF_YEAR, 1);

	   return calendar.getTime();
	}
	
	
	public static Date getDataInicio(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, 1);
		//c.add(Calendar.DAY_OF_MONTH, -60);
		c.add(Calendar.MONTH, -2);
		return c.getTime();
	}
	
	public static Date getDataMaisDias(Date data){
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 30);
		//c.add(Calendar.DAY_OF_MONTH, -60);
		//c.add(Calendar.MONTH, -2);
		return c.getTime();
	}
	
	
	public static Date getDataFinal(Date data){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	

	public static Date converteDataSql(String data){
		
		String[] date = data.split("-");
		Calendar calendar =  Calendar.getInstance();
	 
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		calendar.set(Integer.valueOf(date[2]), Integer.valueOf(date[1]) - 1, Integer.valueOf(date[0]));
		
		return calendar.getTime();
		
		
	} 

}
