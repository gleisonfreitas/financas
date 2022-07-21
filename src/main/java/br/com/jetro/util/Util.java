package br.com.jetro.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static String formatarData(Date data){
		return simpleDateFormat.format(data);
	}   
	
	public static String retornarMoedaBr(Double valor){
		
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		numberFormat.setMinimumFractionDigits(2);
		
		return numberFormat.format(valor);
		
	}
	
	public static String retornarNumeroBr(Double valor){
		
		NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt","BR"));
		numberFormat.setMinimumFractionDigits(2);
		
		return numberFormat.format(valor);
		
	}

	public static String retornarNomeMes(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		return sdf.format(data);
	}
	
	public static String removerFormatacao(String valor) {
		return valor.replaceAll("\\.|-|\\(|\\)", "");
	}

}
