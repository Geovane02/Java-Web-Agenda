package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe JavaBeans;
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String read = "Select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// o la�o a baixo vai executar se tiver contatos salvos no Banco de Dados.
			while (rs.next()) {
				// variaveis recebendo dados do banco.
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// Populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	/** CRUD UPDATE **/
	// Selecionar o contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//Editar contato
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
