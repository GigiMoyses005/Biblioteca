package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PessoaDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
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

    public Pessoa validarLogin(String email, String senha) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT * FROM pessoa WHERE email = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senha);

        java.sql.ResultSet rs = stmt.executeQuery();
        Pessoa pessoa = null;
        if (rs.next()) {
            pessoa = new Pessoa();
            pessoa.setId(rs.getInt("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setDataNascimento(rs.getString("data_nascimento"));
            pessoa.setEndereco(rs.getString("endereco"));
            pessoa.setGenero(rs.getString("genero"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setSenha(rs.getString("senha"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return pessoa;
    }

    public void atualizarPessoa(Pessoa pessoa, String emailOriginal) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "UPDATE pessoa SET nome = ?, data_nascimento = ?, endereco = ?, genero = ?, telefone = ?, email = ?, senha = ? WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, pessoa.getNome());
        stmt.setString(2, pessoa.getDataNascimento());
        stmt.setString(3, pessoa.getEndereco());
        stmt.setString(4, pessoa.getGenero());
        stmt.setString(5, pessoa.getTelefone());
        stmt.setString(6, pessoa.getEmail());
        stmt.setString(7, pessoa.getSenha());
        stmt.setString(8, emailOriginal);

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public Pessoa buscarPorEmail(String email) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT * FROM pessoa WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        java.sql.ResultSet rs = stmt.executeQuery();
        Pessoa pessoa = null;
        if (rs.next()) {
            pessoa = new Pessoa();
            pessoa.setId(rs.getInt("id"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setDataNascimento(rs.getString("data_nascimento"));
            pessoa.setEndereco(rs.getString("endereco"));
            pessoa.setGenero(rs.getString("genero"));
            pessoa.setTelefone(rs.getString("telefone"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setSenha(rs.getString("senha"));
        }
        rs.close();
        stmt.close();
        conn.close();
        return pessoa;
    }
}
