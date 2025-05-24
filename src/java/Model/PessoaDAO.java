package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PessoaDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    public void cadastrarPessoa(Pessoa pessoa) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "INSERT INTO pessoa (nome, data_nascimento, endereco, genero, telefone, email, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pessoa.getNome());
        stmt.setString(2, pessoa.getDataNascimento());
        stmt.setString(3, pessoa.getEndereco());
        stmt.setString(4, pessoa.getGenero());
        stmt.setString(5, pessoa.getTelefone());
        stmt.setString(6, pessoa.getEmail());
        stmt.setString(7, pessoa.getSenha());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
