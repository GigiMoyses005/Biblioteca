package Controller;

import Model.LocacaoDAO;
import Model.Pessoa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/DevolverLivro")
public class DevolverLivroServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        try {
            // Receber ID da locação
            String idLocacaoStr = request.getParameter("idLocacao");
            
            if (idLocacaoStr == null || idLocacaoStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/MeusLivros");
                return;
            }

            int idLocacao = Integer.parseInt(idLocacaoStr);

            // Processar devolução
            LocacaoDAO locacaoDAO = new LocacaoDAO();
            locacaoDAO.devolverLivro(idLocacao);

            // Redirecionar com mensagem de sucesso
            response.sendRedirect(request.getContextPath() + "/MeusLivros?msg=devolvido");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/MeusLivros?msg=erro");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
} 