package br.com.jetro.beans;

import java.util.HashMap;
import java.util.Map;


public class Teste {
	
	public static void main(String[] args) {
		
		
		Map<String, Double> map = new HashMap<String, Double>();
		
		map.put("Teste", 200.0);
		
		Double valor = map.get("Teste");
		
		valor += 150.0;
		
		map.put("Teste", valor);
		
		System.out.println(map);
		
		
		
	}

}
