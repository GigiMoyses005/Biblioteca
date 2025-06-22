<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.catalogoDAO"%>
<%@page import="Model.catalogoDAO.LivroDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Livros</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Lista de Livros</h2>
            
            <% if (request.getParameter("mensagem") != null) { %>
                <div class="alert alert-success">
                    <%= request.getParameter("mensagem") %>
                </div>
            <% } %>
            
            <% if (request.getParameter("erro") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getParameter("erro") %>
                </div>
            <% } %>
            
            <div class="mb-3">
                <a href="CadastroLivro.html" class="btn btn-primary">Adicionar Novo Livro</a>
            </div>
            
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>Ano</th>
                            <th>Gênero</th>
                            <th>Imagem</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            catalogoDAO dao = new catalogoDAO();
                            List<LivroDTO> livros = dao.listarLivros();
                            
                            for (LivroDTO livro : livros) {
                        %>
                        <tr>
                            <td><%= livro.titulo %></td>
                            <td><%= livro.autor %></td>
                            <td><%= livro.ano %></td>
                            <td><%= livro.genero %></td>
                            <td>
                                <% if (livro.imagem != null && !livro.imagem.isEmpty()) { %>
                                    <img src="<%= livro.imagem %>" alt="Capa do livro" style="max-width: 100px;">
                                <% } else { %>
                                    Sem imagem
                                <% } %>
                            </td>
                            <td>
                                <a href="editarLivro.jsp?titulo=<%= livro.titulo %>" class="btn btn-warning btn-sm">Editar</a>
                                <a href="removerLivro?titulo=<%= livro.titulo %>" class="btn btn-danger btn-sm" 
                                   onclick="return confirm('Tem certeza que deseja remover este livro?')">Remover</a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 