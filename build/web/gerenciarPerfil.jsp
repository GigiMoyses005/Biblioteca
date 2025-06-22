<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Model.Pessoa" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerenciar Perfil - Bookfy</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap" rel="stylesheet">
    <style>
        body {
            background: #f7f7f7;
            font-family: 'Roboto', Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .navbar {
            background: #f4f4f4;
            padding: 24px 40px 16px 40px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.03);
        }
        .navbar .logo {
            font-size: 2rem;
            font-weight: bold;
            color: #222;
            letter-spacing: 1px;
        }
        .navbar ul {
            list-style: none;
            display: flex;
            gap: 32px;
            margin: 0;
            padding: 0;
        }
        .navbar ul li a {
            text-decoration: none;
            color: #2c3e50;
            font-size: 1.1rem;
            font-weight: 500;
            transition: color 0.2s;
        }
        .navbar ul li a:hover {
            color: #1976d2;
        }
        .container {
            max-width: 800px;
            margin: 40px auto;
            padding: 0 20px;
        }
        .card {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
            padding: 40px;
        }
        .card h1 {
            color: #2c3e50;
            margin-bottom: 30px;
            text-align: center;
            font-size: 2rem;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #2c3e50;
            font-weight: 500;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e1e8ed;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.2s;
            box-sizing: border-box;
        }
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #1976d2;
        }
        .form-row {
            display: flex;
            gap: 20px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .btn {
            background: #1976d2;
            color: white;
            padding: 14px 28px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        .btn:hover {
            background: #1565c0;
        }
        .btn-secondary {
            background: #6c757d;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .btn-danger {
            background: #dc3545;
        }
        .btn-danger:hover {
            background: #c82333;
        }
        .btn-container {
            display: flex;
            gap: 16px;
            justify-content: center;
            margin-top: 30px;
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
            background: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 30px;
            text-align: center;
        }
        .user-info h3 {
            margin: 0 0 10px 0;
            color: #2c3e50;
        }
        .user-info p {
            margin: 5px 0;
            color: #6c757d;
        }
        @media (max-width: 768px) {
            .form-row {
                flex-direction: column;
                gap: 0;
            }
            .btn-container {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="logo">
            <img src="imagens/logo_bookfy.png" alt="Bookfy Logo" style="height:80px; vertical-align:middle;">
        </div>
        <ul>
            <li><a href="Home.html">Home</a></li>
            <li><a href="/Biblioteca/catalogo">Catálogo</a></li>
            <li><a href="books">Meus Livros</a></li>
            <li><a href="/Biblioteca/LoginPessoa">Login</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="card">
            <h1>Gerenciar Perfil</h1>
            
            <% 
            // Verificar se há mensagens de sucesso ou erro
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
            %>

            <div class="user-info">
                <h3>Bem-vindo, <%= usuario.getNome() %>!</h3>
                <p>Email: <%= usuario.getEmail() %></p>
                <p>Você está logado e pode editar suas informações abaixo.</p>
            </div>

            <form action="AtualizarPerfil" method="post">
                <div class="form-row">
                    <div class="form-group">
                        <label for="nome">Nome Completo</label>
                        <input type="text" id="nome" name="nome" value="<%= usuario.getNome() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="dataNascimento">Data de Nascimento</label>
                        <input type="date" id="dataNascimento" name="dataNascimento" value="<%= usuario.getDataNascimento() %>" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="endereco">Endereço</label>
                    <input type="text" id="endereco" name="endereco" value="<%= usuario.getEndereco() %>" required>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="genero">Gênero</label>
                        <select id="genero" name="genero" required>
                            <option value="">Selecione...</option>
                            <option value="Masculino" <%= "Masculino".equals(usuario.getGenero()) ? "selected" : "" %>>Masculino</option>
                            <option value="Feminino" <%= "Feminino".equals(usuario.getGenero()) ? "selected" : "" %>>Feminino</option>
                            <option value="Outro" <%= "Outro".equals(usuario.getGenero()) ? "selected" : "" %>>Outro</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="telefone">Telefone</label>
                        <input type="tel" id="telefone" name="telefone" value="<%= usuario.getTelefone() %>" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" value="<%= usuario.getEmail() %>" required>
                </div>

                <div class="form-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" value="<%= usuario.getSenha() %>" required>
                </div>

                <div class="btn-container">
                    <button type="submit" class="btn">Atualizar Perfil</button>
                    <a href="Home.html" class="btn btn-secondary">Voltar</a>
                    <a href="Logout" class="btn btn-danger">Sair</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html> 