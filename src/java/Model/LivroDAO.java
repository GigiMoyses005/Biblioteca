package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LivroDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca";
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
}
