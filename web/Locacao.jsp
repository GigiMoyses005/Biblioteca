<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Pessoa" %>
<%@ page import="Model.Livro" %>
<%@ page import="Model.LivroDAO" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Locação de Livro - Bookfy</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        :root {
            --azul-escuro: #2C3E50;
            --azul-claro: #3498DB;
            --cinza-claro: #ECF0F1;
            --cinza-medio: #95A5A6;
            --branco: #FFFFFF;
            --verde: #27AE60;
            --vermelho: #E74C3C;
        }
        body {
            background-color: var(--branco);
            color: var(--azul-escuro);
            font-family: 'Roboto', Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .navbar {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            background: #f4f4f4;
            padding: 16px 40px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            z-index: 1000;
        }
        .navbar .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: #222;
        }
        .navbar ul {
            list-style: none;
            display: flex;
            gap: 24px;
            margin: 0;
            padding: 0;
        }
        .navbar ul li a {
            text-decoration: none;
            color: #2c3e50;
            font-size: 1rem;
            font-weight: 500;
            transition: color 0.2s;
        }
        .navbar ul li a:hover {
            color: #1976d2;
        }
        .form-container {
            margin-top: 80px;
            background-color: var(--cinza-claro);
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }
        .form-container h1 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
            color: var(--azul-escuro);
            border-bottom: 2px solid var(--azul-claro);
            padding-bottom: 15px;
        }
        .livro-info {
            background: var(--branco);
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid var(--azul-claro);
        }
        .livro-info h3 {
            margin: 0 0 10px 0;
            color: var(--azul-escuro);
        }
        .livro-info p {
            margin: 5px 0;
            color: var(--cinza-medio);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: var(--azul-escuro);
        }
        .form-group input[type="text"],
        .form-group input[type="date"] {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid var(--cinza-medio);
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
            box-sizing: border-box;
        }
        .form-group input:focus {
            outline: none;
            border-color: var(--azul-claro);
        }
        .form-group input[readonly] {
            background-color: #f8f9fa;
            color: var(--cinza-medio);
        }
        .btn {
            background: var(--azul-claro);
            color: var(--branco);
            border: none;
            padding: 14px 28px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background: var(--azul-escuro);
        }
        .btn:disabled {
            background: var(--cinza-medio);
            cursor: not-allowed;
        }
        .alert {
            padding: 16px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .user-info {
            background: #e8f4fd;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
            text-align: center;
        }
        .user-info p {
            margin: 5px 0;
            color: var(--azul-escuro);
        }
        .voltar-link {
            text-align: center;
            margin-top: 20px;
        }
        .voltar-link a {
            color: var(--azul-claro);
            text-decoration: none;
            font-weight: 500;
        }
        .voltar-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="logo">
            <img src="imagens/logo_bookfy.png" alt="Bookfy Logo" style="height:60px; vertical-align:middle;">
        </div>
        <ul>
            <li><a href="Home.html">Home</a></li>
            <li><a href="/Biblioteca/catalogo">Catálogo</a></li>
            <li><a href="/Biblioteca/MeusLivros">Meus Livros</a></li>
            <li><a href="gerenciarPerfil.jsp">Minha Conta</a></li>
        </ul>
    </nav>

    <div class="form-container">
        <h1>Locação de Livro</h1>
        
        <% 
        // Verificar se há mensagens
        String mensagem = (String) request.getAttribute("mensagem");
        String erro = (String) request.getAttribute("erro");
        
        if (mensagem != null) {
        %>
            <div class="alert alert-success">
                <%= mensagem %>
            </div>
        <%
        }
        
        if (erro != null) {
        %>
            <div class="alert alert-error">
                <%= erro %>
            </div>
        <%
        }
        
        // Obter dados do usuário logado
        Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        
        // Obter dados do livro
        String idLivroStr = (String) request.getAttribute("idLivro");
        String tituloLivro = (String) request.getAttribute("tituloLivro");
        
        // Buscar informações completas do livro se ID foi fornecido
        Livro livro = null;
        if (idLivroStr != null && !idLivroStr.trim().isEmpty()) {
            try {
                LivroDAO livroDAO = new LivroDAO();
                livro = livroDAO.buscarPorId(Integer.parseInt(idLivroStr));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Definir data mínima (hoje)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataHoje = sdf.format(new Date());
        %>

        <div class="user-info">
            <p><strong>Usuário:</strong> <%= usuario.getNome() %></p>
            <p><strong>Email:</strong> <%= usuario.getEmail() %></p>
        </div>

        <% if (livro != null) { %>
            <div class="livro-info">
                <h3>📚 Informações do Livro</h3>
                <p><strong>Título:</strong> <%= livro.getTitulo() %></p>
                <p><strong>Autor:</strong> <%= livro.getAutor() %></p>
                <p><strong>Ano:</strong> <%= livro.getAno() %></p>
                <p><strong>Gênero:</strong> <%= livro.getGenero() %></p>
            </div>
        <% } else if (tituloLivro != null && !tituloLivro.isEmpty()) { %>
            <div class="livro-info">
                <h3>📚 Informações do Livro</h3>
                <p><strong>Título:</strong> <%= tituloLivro %></p>
            </div>
        <% } %>

        <form action="ProcessarLocacao" method="post">
            <input type="hidden" name="idLivro" value="<%= idLivroStr != null ? idLivroStr : "" %>">
            
            <div class="form-group">
                <label for="dataInicio">📅 Data de Início da Locação</label>
                <input type="date" id="dataInicio" name="dataInicio" min="<%= dataHoje %>" required>
            </div>

            <div class="form-group">
                <label for="dataFim">📅 Data de Devolução</label>
                <input type="date" id="dataFim" name="dataFim" min="<%= dataHoje %>" required>
            </div>

            <button type="submit" class="btn">✅ Confirmar Locação</button>
        </form>

        <div class="voltar-link">
            <a href="/Biblioteca/catalogo">← Voltar ao Catálogo</a>
        </div>
    </div>

    <script>
        // Validação de datas no frontend
        document.getElementById('dataInicio').addEventListener('change', function() {
            const dataInicio = this.value;
            const dataFimInput = document.getElementById('dataFim');
            
            if (dataInicio) {
                dataFimInput.min = dataInicio;
                if (dataFimInput.value && dataFimInput.value < dataInicio) {
                    dataFimInput.value = dataInicio;
                }
            }
        });

        // Definir data mínima para hoje
        const hoje = new Date().toISOString().split('T')[0];
        document.getElementById('dataInicio').min = hoje;
        document.getElementById('dataFim').min = hoje;
    </script>
</body>
</html> 