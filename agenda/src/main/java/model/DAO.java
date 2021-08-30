package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DAO {
	/** M�dulo de Conex�o **/
	// Par�metros de conex�o
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "281024";

	// M�todo de conex�o
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver); // vai ler o driver do banco de dados
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	// teste de conex�o
	// public void testeConexao() {
	// try {
	// Connection con = conectar();
	// System.out.println(con);
	// con.close();
	// } catch (Exception e) {
	// System.out.println(e);
	// }
	// }

	/** CRUD CREAT **/
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			// abrir a conex�o com o banco
			Connection con = conectar();
			// preparar a query para execu��o no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parametros (?) pelo conte�do das variaveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Encerrar a conex�o com o Banco de Dados
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
