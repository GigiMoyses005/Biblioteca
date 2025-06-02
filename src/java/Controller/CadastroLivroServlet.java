package Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.LivroDAO;

@WebServlet("/CadastroLivro")
@MultipartConfig
public class CadastroLivroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebendo os dados do formulário
        String titulo = request.getParameter("livro");
        String autor = request.getParameter("autor");
        String ano = request.getParameter("ano");
        String genero = request.getParameter("genero");

        // Recebendo a imagem (upload)
        Part filePart = request.getPart("imagem");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Caminho absoluto fora da aplicação
        String uploadPath = "C:\\UploadsLivros\\imagens";

        // Cria o diretório caso não exista
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Caminho completo do arquivo
        String caminhoArquivo = uploadPath + File.separator + fileName;

        // Copia o arquivo para o diretório
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(caminhoArquivo), StandardCopyOption.REPLACE_EXISTING);
        }

        // Salvar dados no banco
        try {
            LivroDAO dao = new LivroDAO();
            dao.cadastrarLivro(titulo, autor, ano, genero, fileName);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2>Livro cadastrado com sucesso!</h2>");
            out.println("<a href='Home.html'>Voltar</a>");

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar o livro", e);
        }
    }
}
