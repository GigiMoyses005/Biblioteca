package Controller;

import Model.Locacao;
import Model.LocacaoDAO;
import Model.Pessoa;
import Model.PessoaDAO;
import Model.Livro;
import Model.LivroDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/ProcessarLocacao")
public class LocacaoServlet extends HttpServlet {
    
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

        try {
            // Receber parâmetros do formulário
            String idLivroStr = request.getParameter("idLivro");
            String dataInicioStr = request.getParameter("dataInicio");
            String dataFimStr = request.getParameter("dataFim");

            // Validar parâmetros
            if (idLivroStr == null || idLivroStr.trim().isEmpty() ||
                dataInicioStr == null || dataInicioStr.trim().isEmpty() ||
                dataFimStr == null || dataFimStr.trim().isEmpty()) {
                
                request.setAttribute("erro", "Todos os campos são obrigatórios!");
                request.getRequestDispatcher("Locacao.jsp").forward(request, response);
                return;
            }

            int idLivro = Integer.parseInt(idLivroStr);

            // Converter datas
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dataInicio = sdf.parse(dataInicioStr);
            Date dataFim = sdf.parse(dataFimStr);

            // Validar datas
            Date hoje = new Date();
            if (dataInicio.before(hoje)) {
                request.setAttribute("erro", "A data de início não pode ser anterior a hoje!");
                request.getRequestDispatcher("Locacao.jsp").forward(request, response);
                return;
            }

            if (dataFim.before(dataInicio)) {
                request.setAttribute("erro", "A data de devolução deve ser posterior à data de início!");
                request.getRequestDispatcher("Locacao.jsp").forward(request, response);
                return;
            }

            // Verificar disponibilidade do livro
            LocacaoDAO locacaoDAO = new LocacaoDAO();
            if (!locacaoDAO.verificarDisponibilidade(idLivro)) {
                request.setAttribute("erro", "Este livro não está disponível para locação no momento!");
                request.getRequestDispatcher("Locacao.jsp").forward(request, response);
                return;
            }

            // Criar locação
            Locacao locacao = new Locacao();
            locacao.setIdPessoa(usuarioLogado.getId());
            locacao.setIdLivro(idLivro);
            locacao.setDataInicio(dataInicio);
            locacao.setDataFim(dataFim);
            locacao.setStatus("ativa");

            // Salvar no banco
            locacaoDAO.criarLocacao(locacao);

            // Redirecionar com mensagem de sucesso
            request.setAttribute("mensagem", "Locação realizada com sucesso!");
            request.getRequestDispatcher("Locacao.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao processar locação. Tente novamente.");
            request.getRequestDispatcher("Locacao.jsp").forward(request, response);
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

        // Receber parâmetros da URL
        String idLivroStr = request.getParameter("idLivro");
        String tituloLivro = request.getParameter("titulo");

        // Verificar disponibilidade
        try {
            if (idLivroStr != null && !idLivroStr.trim().isEmpty()) {
                int idLivro = Integer.parseInt(idLivroStr);
                LocacaoDAO locacaoDAO = new LocacaoDAO();
                if (!locacaoDAO.verificarDisponibilidade(idLivro)) {
                    request.setAttribute("erro", "Este livro não está disponível para locação no momento!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Adicionar dados do livro à requisição
        request.setAttribute("idLivro", idLivroStr);
        request.setAttribute("tituloLivro", tituloLivro);
        request.setAttribute("usuarioLogado", usuarioLogado);

        // Redirecionar para a página de locação
        request.getRequestDispatcher("Locacao.jsp").forward(request, response);
    }
} 