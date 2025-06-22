package Controller;

import Model.catalogoDAO;
import Model.catalogoDAO.LivroDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/atualizarLivro")
public class AtualizarLivroServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redireciona para a página de edição
        response.sendRedirect("editarLivro.jsp");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String ano = request.getParameter("ano");
        String genero = request.getParameter("genero");
        String imagem = request.getParameter("imagem");
        
        if (titulo == null || titulo.trim().isEmpty()) {
            response.sendRedirect("listarLivros.jsp?erro=Título do livro não fornecido");
            return;
        }
        
        catalogoDAO dao = new catalogoDAO();
        LivroDTO livro = dao.buscarLivroPorTitulo(titulo);
        
        if (livro == null) {
            response.sendRedirect("listarLivros.jsp?erro=Livro não encontrado");
            return;
        }
        
        // Atualiza os dados do livro
        livro.titulo = titulo;
        livro.autor = autor;
        livro.ano = ano;
        livro.genero = genero;
        livro.imagem = imagem;
        
        boolean sucesso = dao.atualizarLivro(livro);
        
        if (sucesso) {
            response.sendRedirect("listarLivros.jsp?mensagem=Livro atualizado com sucesso!");
        } else {
            response.sendRedirect("editarLivro.jsp?titulo=" + titulo + "&erro=Erro ao atualizar livro");
        }
    }
} 