package br.com.jetro.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaUtil {
	
	
	public static Connection connection;

	public static void criarConexao(){
		
		String url = "jdbc:postgres://localhost:5432/financeiro";
		try {
			connection = DriverManager.getConnection(url, "postgres", "123");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
