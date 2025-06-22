package Controller;

import Model.Pessoa;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/gerenciarPerfil.jsp", "/AtualizarPerfil"})
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização do filtro
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // Verificar se o usuário está logado
        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);
        
        if (isLoggedIn) {
            // Usuário está logado, continuar com a requisição
            chain.doFilter(request, response);
        } else {
            // Usuário não está logado, redirecionar para login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login.jsp");
        }
    }
    
    @Override
    public void destroy() {
        // Limpeza do filtro
    }
} 