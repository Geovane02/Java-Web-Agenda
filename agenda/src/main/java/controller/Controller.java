package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")){
			novoContato(request,response);
		}else {
			response.sendRedirect("index.html");
		}
		// teste de conexao
		// dao.testeConexao();
	}
	//listar contato
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp");
		//criar um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
			//teste de recebimento de lista
				//for (int i = 0; i < lista.size(); i++) {
				//	System.out.println(lista.get(i).getIdcon());
				//	System.out.println(lista.get(i).getNome());
				//	System.out.println(lista.get(i).getFone());
				//	System.out.println(lista.get(i).getEmail());
				//}
		
		
		
		//encaminhar  a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	//novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//teste de recebimento de dados
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		
		//setar as variaveis javaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		//invocar classe responsavel pela inserção do banco de DADOS
		dao.inserirContato(contato);
		//redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}
	
	
	
	
}
