package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import db.DB;
import entities.Order;
import entities.OrderStatus;
import entities.Product;

public class Program {

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DB.getConnection(); //faz conexão com o DB	
		
		//instancia/ cria um objeto do tipo Statement, que é o comando SQL para o DB
		//uso o comando "st" do tipo Statement para executar a Query
		Statement st = conn.createStatement(); 
		
		//armazena os dados da consulta
		//ResultSet rs = st.executeQuery("select * from tb_order");
		ResultSet rs = st.executeQuery("SELECT * FROM tb_order "
				+ "INNER JOIN tb_order_product ON tb_order.id = tb_order_product.order_id "
				+ "INNER JOIN tb_product ON tb_product.id = tb_order_product.product_id");
		
		
		//Registrando os produtos
		Map<Long, Order> map = new HashMap<>();
		Map<Long, Product> prods = new HashMap<>();
		
		//enquanto houver dado ele mostra
		while (rs.next()) {			
			
			//Instanciando os produtos
			Long orderId = rs.getLong("order_id");
			if(map.get(orderId) == null) {
				Order order = instantiateOrder(rs);
				map.put(orderId, order); //aqui eu salvo
			}
			
			
			Long productId = rs.getLong("product_id");
			if(prods.get(productId) == null) {
				Product p = instantiateProduct(rs);
				prods.put(productId, p);
			}
			
			
			//Associando os produtos
			map.get(orderId).getProducts().add(prods.get(productId));			
		}
		
		//Listando os pedidos
		for(Long orderId : map.keySet()) {
			System.out.println(map.get(orderId));
			for(Product p : map.get(orderId).getProducts()) {
				System.out.println(p);				
			}
			System.out.println();
		}
	}
	
	private static Order instantiateOrder(ResultSet rs) throws SQLException{
		Order order = new Order();
		order.setId(rs.getLong("order_id"));
		order.setLatitude(rs.getDouble("latitude"));
		order.setLongitude(rs.getDouble("longitude"));
		order.setMoment(rs.getTimestamp("moment").toInstant());
		order.setStatus(OrderStatus.values()[rs.getInt("status")]);
		return order;
	}
	
	private static Product instantiateProduct(ResultSet rs) throws SQLException{
		Product p = new Product();
		p.setId(rs.getLong("product_id"));
		p.setName(rs.getString("name"));
		p.setPrice(rs.getDouble("price"));
		p.setDescription(rs.getString("description"));
		p.setImageUri(rs.getString("image_uri"));
		return p;
	}
	
	
}
