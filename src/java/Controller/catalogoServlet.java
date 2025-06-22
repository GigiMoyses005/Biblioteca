package Controller;

import Model.catalogoDAO;
import Model.LocacaoDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.net.URLEncoder;

@WebServlet("/catalogo")
public class catalogoServlet extends HttpServlet {

    private final catalogoDAO dao = new catalogoDAO();

    @Override
    public void init() throws ServletException {
        System.out.println("Servlet catalogoServlet iniciado!");
        super.init();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            System.out.println("=== INICIANDO PROCESSAMENTO DO CATÁLOGO ===");
            List<catalogoDAO.LivroDTO> livros = dao.listarLivros();
            System.out.println("Livros recebidos no Servlet: " + livros.size());

            // Gera a parte dinâmica
            out.println("<!DOCTYPE html>");
            out.println("<html lang='pt-br'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Catálogo de Livros</title>");
            out.println("<style>");
            out.println(":root {");
            out.println("    --azul-escuro: #2C3E50;");
            out.println("    --azul-claro: #3498DB;");
            out.println("    --cinza-claro: #ECF0F1;");
            out.println("    --cinza-medio: #95A5A6;");
            out.println("    --branco: #FFFFFF;");
            out.println("    --verde: #27AE60;");
            out.println("    --vermelho: #E74C3C;");
            out.println("}");
            out.println("body {");
            out.println("    background-color: var(--branco);");
            out.println("    color: var(--azul-escuro);");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    margin: 0;");
            out.println("    padding: 20px;");
            out.println("}");
            out.println("h1, h2 {");
            out.println("    text-align: center;");
            out.println("    margin-bottom: 20px;");
            out.println("    font-size: 28px;");
            out.println("    color: var(--azul-escuro);");
            out.println("    border-bottom: 2px solid var(--azul-claro);");
            out.println("    padding-bottom: 10px;");
            out.println("}");
            out.println("section {");
            out.println("    background-color: var(--cinza-claro);");
            out.println("    padding: 30px;");
            out.println("    border-radius: 10px;");
            out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);");
            out.println("    width: 100%;");
            out.println("    max-width: 800px;");
            out.println("    margin: 0 auto;");
            out.println("}");
            out.println(".livro {");
            out.println("    background-color: var(--branco);");
            out.println("    margin-bottom: 20px;");
            out.println("    padding: 20px;");
            out.println("    border: 1px solid var(--cinza-medio);");
            out.println("    border-radius: 5px;");
            out.println("}");
            out.println(".livro h3 {");
            out.println("    color: var(--azul-escuro);");
            out.println("    margin-top: 0;");
            out.println("    border-bottom: 1px solid var(--azul-claro);");
            out.println("    padding-bottom: 10px;");
            out.println("}");
            out.println(".livro-info {");
            out.println("    margin-left: 20px;");
            out.println("}");
            out.println(".livro-info p {");
            out.println("    margin: 10px 0;");
            out.println("}");
            out.println(".livro-info strong {");
            out.println("    color: var(--azul-escuro);");
            out.println("}");
            out.println(".livro img {");
            out.println("    max-width: 150px;");
            out.println("    height: auto;");
            out.println("    display: block;");
            out.println("    margin: 10px 0;");
            out.println("    border-radius: 5px;");
            out.println("    box-shadow: 0 2px 5px rgba(0,0,0,0.1);");
            out.println("}");
            out.println(".btn {");
            out.println("    background-color: var(--azul-claro);");
            out.println("    color: #fff;");
            out.println("    border: none;");
            out.println("    padding: 8px 16px;");
            out.println("    border-radius: 5px;");
            out.println("    cursor: pointer;");
            out.println("    text-decoration: none;");
            out.println("    display: inline-block;");
            out.println("    margin-right: 10px;");
            out.println("    transition: background-color 0.3s;");
            out.println("}");
            out.println(".btn:hover {");
            out.println("    background-color: var(--azul-escuro);");
            out.println("}");
            out.println(".btn-disabled {");
            out.println("    background-color: var(--cinza-medio);");
            out.println("    cursor: not-allowed;");
            out.println("}");
            out.println(".btn-disabled:hover {");
            out.println("    background-color: var(--cinza-medio);");
            out.println("}");
            out.println(".status-indisponivel {");
            out.println("    color: var(--vermelho);");
            out.println("    font-weight: bold;");
            out.println("    font-size: 14px;");
            out.println("}");
            out.println(".status-disponivel {");
            out.println("    color: var(--verde);");
            out.println("    font-weight: bold;");
            out.println("    font-size: 14px;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Catálogo de Livros</h1>");
            out.println("<section>");
            
            if (livros.isEmpty()) {
                out.println("<p style='text-align: center;'><em>Nenhum livro cadastrado.</em></p>");
            } else {
                LocacaoDAO locacaoDAO = new LocacaoDAO();
                
                for (catalogoDAO.LivroDTO livro : livros) {
                    System.out.println("=== PROCESSANDO LIVRO ===");
                    System.out.println("ID: " + livro.id);
                    System.out.println("Título: " + livro.titulo);
                    System.out.println("Imagem original: " + livro.imagem);
                    
                    // Verificar disponibilidade usando o ID do livro
                    boolean disponivel = locacaoDAO.verificarDisponibilidade(livro.id);
                    System.out.println("Disponível: " + disponivel);
                    
                    out.println("<div class='livro'>");
                    out.println("<h3>" + livro.titulo + "</h3>");
                    out.println("<div class='livro-info-flex' style='display: flex; align-items: center; gap: 24px;'>");
                    
                    // Debug da imagem
                    if (livro.imagem != null && !livro.imagem.trim().isEmpty()) {
                        System.out.println("✅ Livro tem imagem: " + livro.imagem);
                        out.println("<div class='livro-imagem'>");
                        out.println("<img src='" + livro.imagem + "' alt='Capa do livro " + livro.titulo + "' style='max-width: 120px; height: auto; border-radius: 5px;'>");
                        out.println("</div>");
                    } else {
                        System.out.println("❌ Livro NÃO tem imagem");
                    }
                    
                    out.println("<div>");
                    out.println("<p><strong>Autor:</strong> " + livro.autor + "</p>");
                    out.println("<p><strong>Ano:</strong> " + livro.ano + "</p>");
                    out.println("<p><strong>Gênero:</strong> " + livro.genero + "</p>");
                    
                    // Status de disponibilidade
                    if (disponivel) {
                        out.println("<p class='status-disponivel'>✅ Disponível para locação</p>");
                    } else {
                        out.println("<p class='status-indisponivel'>❌ Indisponível</p>");
                    }
                    
                    String tituloEncoded = "";
                    try {
                        tituloEncoded = URLEncoder.encode(livro.titulo, "UTF-8");
                    } catch (Exception e) {
                        tituloEncoded = livro.titulo;
                    }
                    
                    if (disponivel) {
                        out.println("<form action='ProcessarLocacao' method='get' style='margin-top: 10px; display: inline;'>");
                        out.println("<input type='hidden' name='idLivro' value='" + livro.id + "'>");
                        out.println("<input type='hidden' name='titulo' value='" + tituloEncoded + "'>");
                        out.println("<button type='submit' class='btn'>📚 Alugar</button>");
                        out.println("</form>");
                    } else {
                        out.println("<button class='btn btn-disabled' disabled>📚 Indisponível</button>");
                    }
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                }
            }

            out.println("</section>");
            out.println("</body></html>");
            System.out.println("=== CATÁLOGO GERADO COM SUCESSO ===");
        } catch (Exception e) {
            System.out.println("❌ Erro ao processar catálogo: " + e.getMessage());
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h1>Erro ao carregar catálogo</h1>");
            out.println("<p>Ocorreu um erro ao carregar o catálogo. Por favor, tente novamente mais tarde.</p>");
            out.println("<p>Erro: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        }
    }
}
