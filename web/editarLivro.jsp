<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.catalogoDAO"%>
<%@page import="Model.catalogoDAO.LivroDTO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar Livro</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Editar Livro</h2>
            
            <% 
                String titulo = request.getParameter("titulo");
                if (titulo == null || titulo.trim().isEmpty()) {
                    response.sendRedirect("listarLivros.jsp?erro=Título do livro não fornecido");
                    return;
                }
                
                catalogoDAO dao = new catalogoDAO();
                LivroDTO livro = dao.buscarLivroPorTitulo(titulo);
                
                if (livro != null) {
            %>
            
            <% if (request.getParameter("erro") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getParameter("erro") %>
                </div>
            <% } %>
            
            <form action="atualizarLivro" method="POST" class="mt-4">
                <input type="hidden" name="titulo" value="<%= livro.titulo %>">
                
                <div class="mb-3">
                    <label for="titulo" class="form-label">Título:</label>
                    <input type="text" class="form-control" id="titulo" name="titulo" 
                           value="<%= livro.titulo %>" required>
                </div>
                
                <div class="mb-3">
                    <label for="autor" class="form-label">Autor:</label>
                    <input type="text" class="form-control" id="autor" name="autor" 
                           value="<%= livro.autor %>" required>
                </div>
                
                <div class="mb-3">
                    <label for="ano" class="form-label">Ano:</label>
                    <input type="date" class="form-control" id="ano" name="ano" 
                           value="<%= livro.ano %>" required>
                </div>
                
                <div class="mb-3">
                    <label for="genero" class="form-label">Gênero:</label>
                    <input type="text" class="form-control" id="genero" name="genero" 
                           value="<%= livro.genero %>" required>
                </div>
                
                <div class="mb-3">
                    <label for="imagem" class="form-label">Nome da Imagem:</label>
                    <input type="text" class="form-control" id="imagem" name="imagem" 
                           value="<%= livro.imagem != null ? livro.imagem.replace("imagens/", "") : "" %>">
                </div>
                
                <div class="mb-3">
                    <button type="submit" class="btn btn-primary">Atualizar Livro</button>
                    <a href="listarLivros.jsp" class="btn btn-secondary">Voltar</a>
                </div>
            </form>
            
            <% } else { %>
                <div class="alert alert-danger">
                    Livro não encontrado!
                </div>
                <a href="listarLivros.jsp" class="btn btn-secondary">Voltar</a>
            <% } %>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html> 