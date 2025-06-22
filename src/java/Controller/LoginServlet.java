package Controller;

import Model.Pessoa;
import Model.PessoaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/LoginPessoa")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Receber os parâmetros do formulário de login
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        System.out.println("Tentando login com email: " + email + " e senha: " + senha);

        try {
            // Criar uma instância do DAO
            PessoaDAO dao = new PessoaDAO();

            // Verificar se o usuário existe no banco de dados
            Pessoa pessoa = dao.validarLogin(email, senha);
            System.out.println("Pessoa retornada: " + pessoa);

            System.out.println("Email: " + email + ", Senha: " + senha + ", Pessoa: " + pessoa);

            // Se o usuário for encontrado
            if (pessoa != null) {
                // Criar a sessão para o usuário
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", pessoa);

                // Redireciona para a página inicial ou perfil do usuário
                response.sendRedirect(request.getContextPath() + "/Home.html");
            } else {
                // Se não encontrar o usuário, exibe uma mensagem de erro;
                request.setAttribute("erro", "Usuário ou senha inválidos!");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }

            System.out.println("Resultado do login: " + (pessoa != null ? "SUCESSO" : "FALHA"));
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Erro ao realizar login. Tente novamente mais tarde.</h2>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Login.jsp");
    }
}