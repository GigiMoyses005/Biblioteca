package Controller;

import Model.Pessoa;
import Model.PessoaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CadastroPessoa")
public class CadastroPessoaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar codificação UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getParameter("nome"));
        pessoa.setDataNascimento(request.getParameter("ano"));
        pessoa.setEndereco(request.getParameter("endereco"));
        pessoa.setGenero(request.getParameter("genero"));
        pessoa.setTelefone(request.getParameter("telefone"));
        pessoa.setEmail(request.getParameter("e-mail"));
        pessoa.setSenha(request.getParameter("senha"));

        try {
            PessoaDAO dao = new PessoaDAO();
            dao.cadastrarPessoa(pessoa);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2>Pessoa cadastrada com sucesso!</h2>");
            out.println("<a href='Home.html'>Voltar</a>");
        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar pessoa", e);
        }
    }
}
