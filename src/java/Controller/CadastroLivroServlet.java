package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LivroDAO;

@WebServlet("/CadastroLivro")
public class CadastroLivroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String titulo = request.getParameter("livro");
        String autor = request.getParameter("autor");
        String ano = request.getParameter("ano");
        String genero = request.getParameter("genero");

        try {
            LivroDAO dao = new LivroDAO();
            dao.cadastrarLivro(titulo, autor, ano, genero);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>Livro cadastrado com sucesso!</h2>");

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar o livro", e);
        }
    }
}
