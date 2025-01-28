package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DB.getConnection(); //faz conexão com o DB	
		
		//instancia/ cria um objeto do tipo Statement, que é o comando SQL para o DB
		//uso o comando "st" do tipo Statement para executar a Query
		Statement st = conn.createStatement(); 
		
		//armazena os dados da consulta
		ResultSet rs = st.executeQuery("select * from tb_product"); 
		
		//enquanto houver dado ele mostra
		while (rs.next()) {
			System.out.println(rs.getLong("Id") + ", " + rs.getString("Name"));
		}
	}
}
