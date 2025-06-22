package Controller;

import Model.catalogoDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/removerLivro")
public class RemoverLivroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String titulo = request.getParameter("titulo");
        if (titulo == null || titulo.trim().isEmpty()) {
            response.sendRedirect("listarLivros.jsp?erro=Título do livro não fornecido");
            return;
        }
        
        catalogoDAO dao = new catalogoDAO();
        boolean sucesso = dao.removerLivroPorTitulo(titulo);
        
        if (sucesso) {
            response.sendRedirect("listarLivros.jsp?mensagem=Livro removido com sucesso!");
        } else {
            response.sendRedirect("listarLivros.jsp?erro=Erro ao remover livro");
        }
    }
} 