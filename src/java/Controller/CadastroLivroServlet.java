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

import Model.LivroDAO;

@WebServlet("/CadastroLivro")
@MultipartConfig
public class CadastroLivroServlet extends HttpServlet {
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String titulo = request.getParameter("livro");
        String autor = request.getParameter("autor");
        String ano = request.getParameter("ano");
        String genero = request.getParameter("genero");

       
        Part filePart = request.getPart("imagem");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        System.out.println("Nome do arquivo recebido: " + fileName);

       
        String uploadPath = getServletContext().getRealPath("/imagens");
        System.out.println("Caminho de upload: " + uploadPath);
        
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            System.out.println("Diretório criado: " + created);
        }

        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ServletException("O arquivo enviado não é uma imagem válida");
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String caminhoArquivo = uploadPath + File.separator + uniqueFileName;
        System.out.println("Caminho completo do arquivo: " + caminhoArquivo);

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(caminhoArquivo), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Arquivo copiado com sucesso para: " + caminhoArquivo);
            
            File arquivoSalvo = new File(caminhoArquivo);
            if (arquivoSalvo.exists()) {
                System.out.println("Arquivo existe e tem tamanho: " + arquivoSalvo.length() + " bytes");
            } else {
                System.out.println("ERRO: Arquivo não foi criado!");
            }
        }

        
        try {
            LivroDAO dao = new LivroDAO();
            dao.cadastrarLivro(titulo, autor, ano, genero, uniqueFileName);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<h2>Livro cadastrado com sucesso!</h2>");
            out.println("<a href='Home.html'>Voltar</a>");

        } catch (Exception e) {
            throw new ServletException("Erro ao cadastrar o livro", e);
        }
    }
}
