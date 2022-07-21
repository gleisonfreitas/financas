package br.com.jetro.comparator;

import java.util.Comparator;

public class TipoDescricaoResumoComparator implements Comparator<String>{
	
	@Override
	public int compare(String tipoUm, String tipoDois) {
		
		if(tipoUm.equals(tipoDois)){
			return 0;
		}else if("Saldo inicial".equals(tipoUm)){
			return -1;
		}else if("Saldo inicial".equals(tipoDois)){
			return 1;
		}else if("Receitas".equals(tipoUm)){
			return -1;
		}else if("Receitas".equals(tipoDois)){
			return 1;
		}else if("Despesas".equals(tipoUm)){
			return -1;
		}else if("Despesas".equals(tipoDois)){
			return 1;
		}
		
		return 1;
	}

}
