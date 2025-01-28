package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import entities.Product;

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
			Product p = instantiateProduct(rs);
			
			System.out.println(p);
		}
	}
	
	private static Product instantiateProduct(ResultSet rs) throws SQLException{
		Product p = new Product();
		p.setId(rs.getLong("id"));
		p.setName(rs.getString("name"));
		p.setPrice(rs.getDouble("price"));
		p.setDescription(rs.getString("description"));
		p.setImageUri(rs.getString("image_uri"));
		return p;
	}
}
