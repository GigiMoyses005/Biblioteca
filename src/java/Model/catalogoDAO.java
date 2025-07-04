package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class catalogoDAO {

    private final String url = "jdbc:mysql://localhost:3306/biblioteca?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private final String user = "root";
    private final String password = "";

    public catalogoDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao carregar o driver MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class LivroDTO {

        public int id;
        public String titulo;
        public String autor;
        public String ano;
        public String genero;
        public String imagem;

        public LivroDTO(int id, String titulo, String autor, String ano, String genero, String imagem) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.ano = ano;
            this.genero = genero;
            this.imagem = imagem;
        }
    }

    // CREATE - Adicionar novo livro
    public boolean adicionarLivro(LivroDTO livro) {
        String sql = "INSERT INTO livro (titulo, autor, ano, genero, imagem) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.titulo);
            stmt.setString(2, livro.autor);
            stmt.setString(3, livro.ano);
            stmt.setString(4, livro.genero);
            stmt.setString(5, livro.imagem);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // READ - Listar todos os livros (já existente)
    public List<LivroDTO> listarLivros() {
        List<LivroDTO> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, ano, genero, imagem FROM livro";

        System.out.println("Iniciando listagem de livros...");
        System.out.println("URL do banco: " + url);

        try (
                Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            System.out.println("Conexão com o banco estabelecida com sucesso!");

            while (rs.next()) {
                java.sql.Date dataAno = rs.getDate("ano");
                String anoFormatado = dataAno != null ? dataAno.toString() : "N/A";
                System.out.println("Data do banco: " + dataAno);
                System.out.println("Data formatada: " + anoFormatado);

                String imagemNome = rs.getString("imagem");
                System.out.println("Nome da imagem no banco: " + imagemNome);
                
                // Ajusta o caminho da imagem para ser relativo ao contexto web
                String caminhoImagem = null;
                if (imagemNome != null && !imagemNome.trim().isEmpty()) {
                    caminhoImagem = "imagens/" + imagemNome;
                    System.out.println("Caminho da imagem gerado: " + caminhoImagem);
                } else {
                    System.out.println("Nenhuma imagem encontrada para o livro");
                }

                LivroDTO livro = new LivroDTO(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        anoFormatado,
                        rs.getString("genero"),
                        caminhoImagem
                );
                lista.add(livro);
                System.out.println("Livro encontrado: " + livro.titulo + " (ID: " + livro.id + ")");
            }

            System.out.println("Total de livros encontrados: " + lista.size());

        } catch (SQLException e) {
            System.out.println("❌ Erro ao acessar o banco: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // READ - Buscar livro por ID
    public LivroDTO buscarLivroPorId(int id) {
        String sql = "SELECT id, titulo, autor, ano, genero, imagem FROM livro WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                java.sql.Date dataAno = rs.getDate("ano");
                String anoFormatado = dataAno != null ? dataAno.toString() : "N/A";
                String imagemNome = rs.getString("imagem");
                String caminhoImagem = (imagemNome != null && !imagemNome.trim().isEmpty()) ? 
                                     "imagens/" + imagemNome : null;
                
                return new LivroDTO(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    anoFormatado,
                    rs.getString("genero"),
                    caminhoImagem
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE - Atualizar livro
    public boolean atualizarLivro(LivroDTO livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, ano = ?, genero = ?, imagem = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.titulo);
            stmt.setString(2, livro.autor);
            stmt.setString(3, livro.ano);
            stmt.setString(4, livro.genero);
            stmt.setString(5, livro.imagem);
            stmt.setInt(6, livro.id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Remover livro
    public boolean removerLivro(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método adicional para buscar livros por título
    public List<LivroDTO> buscarLivrosPorTitulo(String titulo) {
        List<LivroDTO> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, ano, genero, imagem FROM livro WHERE titulo LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                java.sql.Date dataAno = rs.getDate("ano");
                String anoFormatado = dataAno != null ? dataAno.toString() : "N/A";
                String imagemNome = rs.getString("imagem");
                String caminhoImagem = (imagemNome != null && !imagemNome.trim().isEmpty()) ? 
                                     "imagens/" + imagemNome : null;
                
                LivroDTO livro = new LivroDTO(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    anoFormatado,
                    rs.getString("genero"),
                    caminhoImagem
                );
                lista.add(livro);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livros por título: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // READ - Buscar livro por título
    public LivroDTO buscarLivroPorTitulo(String titulo) {
        String sql = "SELECT id, titulo, autor, ano, genero, imagem FROM livro WHERE titulo = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                java.sql.Date dataAno = rs.getDate("ano");
                String anoFormatado = dataAno != null ? dataAno.toString() : "N/A";
                String imagemNome = rs.getString("imagem");
                String caminhoImagem = (imagemNome != null && !imagemNome.trim().isEmpty()) ? 
                                     "imagens/" + imagemNome : null;
                
                return new LivroDTO(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    anoFormatado,
                    rs.getString("genero"),
                    caminhoImagem
                );
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro por título: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // DELETE - Remover livro por título
    public boolean removerLivroPorTitulo(String titulo) {
        String sql = "DELETE FROM livro WHERE titulo = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, titulo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
