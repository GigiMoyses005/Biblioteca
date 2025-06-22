package Controller;

import Model.LocacaoDAO;
import Model.Pessoa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/MeusLivros")
public class MeusLivrosServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar codificação UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Verificar se o usuário está logado
        HttpSession session = request.getSession();
        Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
        
        if (usuarioLogado == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }

        try {
            LocacaoDAO locacaoDAO = new LocacaoDAO();
            List<LocacaoDAO.LocacaoDTO> locacoes = locacaoDAO.listarLocacoesPorUsuario(usuarioLogado.getId());
            
            // Atualizar status de locações atrasadas
            locacaoDAO.atualizarStatusAtrasadas();

            // Gerar HTML
            out.println("<!DOCTYPE html>");
            out.println("<html lang='pt-BR'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Meus Livros - Bookfy</title>");
            out.println("<link href='https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap' rel='stylesheet'>");
            out.println("<style>");
            out.println(":root {");
            out.println("    --azul-escuro: #2C3E50;");
            out.println("    --azul-claro: #3498DB;");
            out.println("    --cinza-claro: #ECF0F1;");
            out.println("    --cinza-medio: #95A5A6;");
            out.println("    --branco: #FFFFFF;");
            out.println("    --verde: #27AE60;");
            out.println("    --vermelho: #E74C3C;");
            out.println("    --amarelo: #F39C12;");
            out.println("}");
            out.println("body {");
            out.println("    background-color: var(--branco);");
            out.println("    color: var(--azul-escuro);");
            out.println("    font-family: 'Roboto', Arial, sans-serif;");
            out.println("    margin: 0;");
            out.println("    padding: 20px;");
            out.println("}");
            out.println(".navbar {");
            out.println("    background: #f4f4f4;");
            out.println("    padding: 16px 40px;");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    justify-content: space-between;");
            out.println("    box-shadow: 0 2px 8px rgba(0,0,0,0.1);");
            out.println("    margin-bottom: 30px;");
            out.println("}");
            out.println(".navbar .logo {");
            out.println("    font-size: 1.5rem;");
            out.println("    font-weight: bold;");
            out.println("    color: #222;");
            out.println("}");
            out.println(".navbar ul {");
            out.println("    list-style: none;");
            out.println("    display: flex;");
            out.println("    gap: 24px;");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("}");
            out.println(".navbar ul li a {");
            out.println("    text-decoration: none;");
            out.println("    color: #2c3e50;");
            out.println("    font-size: 1rem;");
            out.println("    font-weight: 500;");
            out.println("    transition: color 0.2s;");
            out.println("}");
            out.println(".navbar ul li a:hover {");
            out.println("    color: #1976d2;");
            out.println("}");
            out.println("h1 {");
            out.println("    text-align: center;");
            out.println("    margin-bottom: 30px;");
            out.println("    font-size: 28px;");
            out.println("    color: var(--azul-escuro);");
            out.println("    border-bottom: 2px solid var(--azul-claro);");
            out.println("    padding-bottom: 10px;");
            out.println("}");
            out.println(".container {");
            out.println("    max-width: 1000px;");
            out.println("    margin: 0 auto;");
            out.println("}");
            out.println(".locacao {");
            out.println("    background-color: var(--cinza-claro);");
            out.println("    margin-bottom: 20px;");
            out.println("    padding: 25px;");
            out.println("    border-radius: 12px;");
            out.println("    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);");
            out.println("    border-left: 4px solid var(--azul-claro);");
            out.println("}");
            out.println(".locacao h3 {");
            out.println("    color: var(--azul-escuro);");
            out.println("    margin-top: 0;");
            out.println("    margin-bottom: 15px;");
            out.println("    font-size: 20px;");
            out.println("}");
            out.println(".locacao-info {");
            out.println("    display: grid;");
            out.println("    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));");
            out.println("    gap: 15px;");
            out.println("    margin-bottom: 20px;");
            out.println("}");
            out.println(".info-item {");
            out.println("    background: var(--branco);");
            out.println("    padding: 12px;");
            out.println("    border-radius: 8px;");
            out.println("    border: 1px solid var(--cinza-medio);");
            out.println("}");
            out.println(".info-item strong {");
            out.println("    color: var(--azul-escuro);");
            out.println("    display: block;");
            out.println("    margin-bottom: 5px;");
            out.println("}");
            out.println(".status {");
            out.println("    display: inline-block;");
            out.println("    padding: 6px 12px;");
            out.println("    border-radius: 20px;");
            out.println("    font-size: 14px;");
            out.println("    font-weight: 600;");
            out.println("    text-transform: uppercase;");
            out.println("}");
            out.println(".status-ativa {");
            out.println("    background: var(--verde);");
            out.println("    color: white;");
            out.println("}");
            out.println(".status-devolvida {");
            out.println("    background: var(--cinza-medio);");
            out.println("    color: white;");
            out.println("}");
            out.println(".status-atrasada {");
            out.println("    background: var(--vermelho);");
            out.println("    color: white;");
            out.println("}");
            out.println(".btn {");
            out.println("    background: var(--azul-claro);");
            out.println("    color: white;");
            out.println("    border: none;");
            out.println("    padding: 10px 20px;");
            out.println("    border-radius: 6px;");
            out.println("    cursor: pointer;");
            out.println("    font-size: 14px;");
            out.println("    text-decoration: none;");
            out.println("    display: inline-block;");
            out.println("    margin-right: 10px;");
            out.println("    transition: background-color 0.3s;");
            out.println("}");
            out.println(".btn:hover {");
            out.println("    background: var(--azul-escuro);");
            out.println("}");
            out.println(".btn-danger {");
            out.println("    background: var(--vermelho);");
            out.println("}");
            out.println(".btn-danger:hover {");
            out.println("    background: #c0392b;");
            out.println("}");
            out.println(".sem-livros {");
            out.println("    text-align: center;");
            out.println("    padding: 40px;");
            out.println("    background: var(--cinza-claro);");
            out.println("    border-radius: 12px;");
            out.println("    color: var(--cinza-medio);");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
            // Navbar
            out.println("<nav class='navbar'>");
            out.println("<div class='logo'>");
            out.println("<img src='imagens/logo_bookfy.png' alt='Bookfy Logo' style='height:60px; vertical-align:middle;'>");
            out.println("</div>");
            out.println("<ul>");
            out.println("<li><a href='Home.html'>Home</a></li>");
            out.println("<li><a href='/Biblioteca/catalogo'>Catálogo</a></li>");
            out.println("<li><a href='MeusLivros.jsp'>Meus Livros</a></li>");
            out.println("<li><a href='gerenciarPerfil.jsp'>Minha Conta</a></li>");
            out.println("</ul>");
            out.println("</nav>");

            out.println("<div class='container'>");
            out.println("<h1>📚 Meus Livros</h1>");
            out.println("<p style='text-align: center; margin-bottom: 30px;'>Bem-vindo, <strong>" + usuarioLogado.getNome() + "</strong>!</p>");

            if (locacoes.isEmpty()) {
                out.println("<div class='sem-livros'>");
                out.println("<h3>📖 Você ainda não tem livros locados</h3>");
                out.println("<p>Explore nosso catálogo e faça sua primeira locação!</p>");
                out.println("<a href='/Biblioteca/catalogo' class='btn'>Ver Catálogo</a>");
                out.println("</div>");
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                for (LocacaoDAO.LocacaoDTO locacao : locacoes) {
                    out.println("<div class='locacao'>");
                    out.println("<h3>📖 " + locacao.getTituloLivro() + "</h3>");
                    
                    out.println("<div class='locacao-info'>");
                    out.println("<div class='info-item'>");
                    out.println("<strong>Autor:</strong> " + locacao.getAutorLivro());
                    out.println("</div>");
                    out.println("<div class='info-item'>");
                    out.println("<strong>Data de Início:</strong> " + sdf.format(locacao.getDataInicio()));
                    out.println("</div>");
                    out.println("<div class='info-item'>");
                    out.println("<strong>Data de Devolução:</strong> " + sdf.format(locacao.getDataFim()));
                    out.println("</div>");
                    out.println("<div class='info-item'>");
                    out.println("<strong>Status:</strong> ");
                    
                    String statusClass = "";
                    String statusText = "";
                    switch (locacao.getStatus()) {
                        case "ativa":
                            statusClass = "status-ativa";
                            statusText = "Ativa";
                            break;
                        case "devolvida":
                            statusClass = "status-devolvida";
                            statusText = "Devolvida";
                            break;
                        case "atrasada":
                            statusClass = "status-atrasada";
                            statusText = "Atrasada";
                            break;
                    }
                    out.println("<span class='status " + statusClass + "'>" + statusText + "</span>");
                    out.println("</div>");
                    out.println("</div>");
                    
                    // Botões de ação
                    if ("ativa".equals(locacao.getStatus())) {
                        out.println("<form action='DevolverLivro' method='post' style='display: inline;'>");
                        out.println("<input type='hidden' name='idLocacao' value='" + locacao.getId() + "'>");
                        out.println("<button type='submit' class='btn btn-danger' onclick='return confirm(\"Confirmar devolução?\")'>📚 Devolver Livro</button>");
                        out.println("</form>");
                    }
                    
                    out.println("</div>");
                }
            }

            out.println("</div>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h1>Erro ao carregar seus livros</h1>");
            out.println("<p>Ocorreu um erro ao carregar suas locações. Por favor, tente novamente mais tarde.</p>");
            out.println("<a href='Home.html'>Voltar ao Início</a>");
            out.println("</body></html>");
        }
    }
} 