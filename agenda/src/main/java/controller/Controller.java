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

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete" })
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
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}

		// teste de conexao
		// dao.testeConexao();
	}

	// listar contato
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp");
		// criar um objeto que irá receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// teste de recebimento de lista
		// for (int i = 0; i < lista.size(); i++) {
		// System.out.println(lista.get(i).getIdcon());
		// System.out.println(lista.get(i).getNome());
		// System.out.println(lista.get(i).getFone());
		// System.out.println(lista.get(i).getEmail());
		// }

		// encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	// novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento de dados
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));

		// setar as variaveis javaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar classe responsavel pela inserção do banco de DADOS
		dao.inserirContato(contato);
		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	// editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon);
		// setar a variavel JavaBeans
		contato.setIdcon(idcon);
		// executar o método selecionar contato (DAO)
		dao.selecionarContato(contato);
		// teste de recebimento
		// System.out.println(contato.getIdcon());
		// System.out.println(contato.getNome());
		// System.out.println(contato.getFone());
		// System.out.println(contato.getEmail());

		// setar os atributos do formulario com o conteúdo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	// editar contato update
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento
		// System.out.println(request.getParameter("idcon"));
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));

		// setar as variaveis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// executar o método alterar contato
		dao.alterarContato(contato);

		// redirecionar para o documento agenda.jsp(atualizando as alterações)
		response.sendRedirect("main");
	}

	// remover um contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato a ser excluido (validador.js)
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon);
		// setar a variavel idcon javaBeans
		contato.setIdcon(idcon);
		// executar o método deletarcontato (DAO) passando o objeto contato como
		// parametro.
		dao.deletarContato(contato);
		// redirecionar para o documento agenda.jsp(atualizando as alterações)
		response.sendRedirect("main");
	}
}
