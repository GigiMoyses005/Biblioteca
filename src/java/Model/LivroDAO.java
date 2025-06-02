package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LivroDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    public void cadastrarLivro(String titulo, String autor, String ano, String genero, String imagem) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "INSERT INTO livro (titulo, autor, ano, genero, imagem) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, titulo);
        stmt.setString(2, autor);
        stmt.setString(3, ano);
        stmt.setString(4, genero);
        stmt.setString(5, imagem); // Caminho ou nome da imagem salva

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
