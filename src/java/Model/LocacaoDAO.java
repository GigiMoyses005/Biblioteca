package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocacaoDAO {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/biblioteca?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    public void criarLocacao(Locacao locacao) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "INSERT INTO locacao (id_pessoa, id_livro, data_inicio, data_fim, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, locacao.getIdPessoa());
        stmt.setInt(2, locacao.getIdLivro());
        stmt.setDate(3, new java.sql.Date(locacao.getDataInicio().getTime()));
        stmt.setDate(4, new java.sql.Date(locacao.getDataFim().getTime()));
        stmt.setString(5, locacao.getStatus());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<LocacaoDTO> listarLocacoesPorUsuario(int idPessoa) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT l.id, l.id_pessoa, l.id_livro, l.data_inicio, l.data_fim, l.data_locacao, l.status, " +
                    "p.nome as nome_pessoa, p.email as email_pessoa, " +
                    "liv.titulo as titulo_livro, liv.autor as autor_livro " +
                    "FROM locacao l " +
                    "INNER JOIN pessoa p ON l.id_pessoa = p.id " +
                    "INNER JOIN livro liv ON l.id_livro = liv.id " +
                    "WHERE l.id_pessoa = ? " +
                    "ORDER BY l.data_inicio DESC";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPessoa);

        ResultSet rs = stmt.executeQuery();
        List<LocacaoDTO> locacoes = new ArrayList<>();
        
        while (rs.next()) {
            LocacaoDTO locacao = new LocacaoDTO();
            locacao.setId(rs.getInt("id"));
            locacao.setIdPessoa(rs.getInt("id_pessoa"));
            locacao.setIdLivro(rs.getInt("id_livro"));
            locacao.setDataInicio(rs.getDate("data_inicio"));
            locacao.setDataFim(rs.getDate("data_fim"));
            locacao.setDataLocacao(rs.getTimestamp("data_locacao"));
            locacao.setStatus(rs.getString("status"));
            locacao.setNomePessoa(rs.getString("nome_pessoa"));
            locacao.setEmailPessoa(rs.getString("email_pessoa"));
            locacao.setTituloLivro(rs.getString("titulo_livro"));
            locacao.setAutorLivro(rs.getString("autor_livro"));
            
            locacoes.add(locacao);
        }
        
        rs.close();
        stmt.close();
        conn.close();
        return locacoes;
    }

    public void devolverLivro(int idLocacao) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "UPDATE locacao SET status = 'devolvida' WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idLocacao);

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public boolean verificarDisponibilidade(int idLivro) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "SELECT COUNT(*) FROM locacao WHERE id_livro = ? AND status = 'ativa'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idLivro);

        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        
        rs.close();
        stmt.close();
        conn.close();
        
        // Se count > 0, o livro está locado
        return count == 0;
    }

    public void atualizarStatusAtrasadas() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

        String sql = "UPDATE locacao SET status = 'atrasada' WHERE data_fim < ? AND status = 'ativa'";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(new Date().getTime()));

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    // Classe DTO para retornar dados completos das locações
    public static class LocacaoDTO {
        private int id;
        private int idPessoa;
        private int idLivro;
        private Date dataInicio;
        private Date dataFim;
        private Date dataLocacao;
        private String status;
        private String nomePessoa;
        private String emailPessoa;
        private String tituloLivro;
        private String autorLivro;

        // Getters e Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public int getIdPessoa() { return idPessoa; }
        public void setIdPessoa(int idPessoa) { this.idPessoa = idPessoa; }

        public int getIdLivro() { return idLivro; }
        public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

        public Date getDataInicio() { return dataInicio; }
        public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

        public Date getDataFim() { return dataFim; }
        public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

        public Date getDataLocacao() { return dataLocacao; }
        public void setDataLocacao(Date dataLocacao) { this.dataLocacao = dataLocacao; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getNomePessoa() { return nomePessoa; }
        public void setNomePessoa(String nomePessoa) { this.nomePessoa = nomePessoa; }

        public String getEmailPessoa() { return emailPessoa; }
        public void setEmailPessoa(String emailPessoa) { this.emailPessoa = emailPessoa; }

        public String getTituloLivro() { return tituloLivro; }
        public void setTituloLivro(String tituloLivro) { this.tituloLivro = tituloLivro; }

        public String getAutorLivro() { return autorLivro; }
        public void setAutorLivro(String autorLivro) { this.autorLivro = autorLivro; }
    }
} 