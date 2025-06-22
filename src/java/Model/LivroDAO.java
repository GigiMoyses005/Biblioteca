package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LivroDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    public void cadastrarLivro(String titulo, String autor, String ano, String genero, String imagem) throws Exception {
        System.out.println("Iniciando cadastro de livro:");
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Ano: " + ano);
        System.out.println("Gênero: " + genero);
        System.out.println("Imagem: " + imagem);

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        System.out.println("Conexão com o banco estabelecida");

        String sql = "INSERT INTO livro (titulo, autor, ano, genero, imagem) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, titulo);
        stmt.setString(2, autor);
        stmt.setString(3, ano);
        stmt.setString(4, genero);
        stmt.setString(5, imagem);

        int rowsAffected = stmt.executeUpdate();
        System.out.println("Registros afetados: " + rowsAffected);

        stmt.close();
        conn.close();
        System.out.println("Conexão fechada");
    }

    public Livro buscarPorId(int id) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT * FROM livro WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        Livro livro = null;
        if (rs.next()) {
            livro = new Livro();
            livro.setId(rs.getInt("id"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setAno(rs.getString("ano"));
            livro.setGenero(rs.getString("genero"));
            livro.setImagem(rs.getString("imagem"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return livro;
    }

    public Livro buscarPorTitulo(String titulo) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT * FROM livro WHERE titulo = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, titulo);

        ResultSet rs = stmt.executeQuery();
        Livro livro = null;
        if (rs.next()) {
            livro = new Livro();
            livro.setId(rs.getInt("id"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setAno(rs.getString("ano"));
            livro.setGenero(rs.getString("genero"));
            livro.setImagem(rs.getString("imagem"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return livro;
    }
}
