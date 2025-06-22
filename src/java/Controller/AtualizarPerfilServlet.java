package Controller;

import Model.Pessoa;
import Model.PessoaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AtualizarPerfil")
public class AtualizarPerfilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar codificação UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Verificar se o usuário está logado
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        // Receber os parâmetros do formulário
        String nome = request.getParameter("nome");
        String dataNascimento = request.getParameter("dataNascimento");
        String endereco = request.getParameter("endereco");
        String genero = request.getParameter("genero");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            // Criar uma instância do DAO
            PessoaDAO dao = new PessoaDAO();

            // Atualizar os dados da pessoa
            Pessoa pessoaAtualizada = new Pessoa();
            pessoaAtualizada.setNome(nome);
            pessoaAtualizada.setDataNascimento(dataNascimento);
            pessoaAtualizada.setEndereco(endereco);
            pessoaAtualizada.setGenero(genero);
            pessoaAtualizada.setTelefone(telefone);
            pessoaAtualizada.setEmail(email);
            pessoaAtualizada.setSenha(senha);

            // Atualizar no banco de dados
            dao.atualizarPessoa(pessoaAtualizada, usuarioLogado.getEmail());

            // Atualizar a sessão com os novos dados
            session.setAttribute("usuarioLogado", pessoaAtualizada);

            // Redirecionar com mensagem de sucesso
            request.setAttribute("mensagem", "Perfil atualizado com sucesso!");
            request.getRequestDispatcher("gerenciarPerfil.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao atualizar perfil. Tente novamente.");
            request.getRequestDispatcher("gerenciarPerfil.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configurar codificação UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Verificar se o usuário está logado
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        // Redirecionar para a página de gerenciamento de perfil
        response.sendRedirect("gerenciarPerfil.jsp");
    }
} 