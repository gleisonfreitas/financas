package br.com.jetro.comparator;

import java.util.Comparator;

public class TipoMesResumoComparator implements Comparator<String>{
	
	@Override
	public int compare(String mesUm, String mesDois) {
		if(mesUm.equals(mesDois)){
			return 0;
		}else if("Janeiro".equals(mesUm)){
			return -1;
		}else if("Janeiro".equals(mesDois)){
			return 1;
		}else if("Fevereiro".equals(mesUm)){
			return -1;
		}else if("Fevereiro".equals(mesDois)){
			return 1;
		}else if("Março".equals(mesUm)){
			return -1;
		}else if("Março".equals(mesDois)){
			return 1;
		}else if("Abril".equals(mesUm)){
			return -1;
		}else if("Abril".equals(mesDois)){
			return 1;
		}else if("Maio".equals(mesUm)){
			return -1;
		}else if("Maio".equals(mesDois)){
			return 1;
		}else if("Junho".equals(mesUm)){
			return -1;
		}else if("Junho".equals(mesDois)){
			return 1;
		}else if("Julho".equals(mesUm)){
			return -1;
		}else if("Julho".equals(mesDois)){
			return 1;
		}else if("Agosto".equals(mesUm)){
			return -1;
		}else if("Agosto".equals(mesDois)){
			return 1;
		}else if("Setembro".equals(mesUm)){
			return -1;
		}else if("Setembro".equals(mesDois)){
			return 1;
		}else if("Outubro".equals(mesUm)){
			return -1;
		}else if("Outubro".equals(mesDois)){
			return 1;
		}else if("Novembro".equals(mesUm)){
			return -1;
		}else if("Novembro".equals(mesDois)){
			return 1;
		}
		
		return 1;
	}

}
