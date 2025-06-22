<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            :root {
                --azul-escuro: #2C3E50;
                --azul-claro: #3498DB;
                --cinza-claro: #ECF0F1;
                --cinza-medio: #95A5A6;
                --branco: #FFFFFF;
            }

            h1{
                text-align: center;
                margin-bottom: 20px;
                font-size: 28px;
                color:#2C3E50;
                border-bottom: 2px solid #3498DB;
                padding-bottom: 10px;
            }

            body {
                background-color: var(--branco);
                color: var(--azul-escuro);
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }

            .form {
                background-color: var(--cinza-claro);
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .form form {
                display: flex;
                flex-direction: column;
            }
            
            .form input[type="email"],
            .form input[type="password"] {
                padding: 8px;
                margin-top: 5px;
                border: 1px solid var(--cinza-medio);
                border-radius: 5px;
                background-color: var(--branco);
            }

            .form label {
                margin-top: 15px;
                font-weight: bold;
            }

            .form input[type="submit"] {
                margin-top: 20px;
                background-color: var(--azul-claro);
                color: var(--branco);
                border: none;
                padding: 10px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .form input[type="submit"]:hover {
                background-color: var(--azul-escuro);
            }
        </style>
    </head>
    <body>
        <div class="form">
            <h1>Login</h1>
            
            <% if (request.getAttribute("erro") != null) { %>
                <div style="color:red; text-align:center; margin-bottom:10px;">
                    <%= request.getAttribute("erro") %>
                </div>
            <% } %>

            <form action="LoginPessoa" method="post">
                <label>E-mail:</label>
                <input type="email" name="email">
                <label>Senha:</label>
                <input type="password" name="senha" required>

                <input type="submit" value="Entrar">
            </form>
        </div>
    </body>
</html> 