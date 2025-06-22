# Sistema de Gerenciamento de Perfil - Bookfy

## Funcionalidades Implementadas

### 1. Página de Gerenciamento de Perfil
- **Arquivo**: `web/gerenciarPerfil.jsp`
- **Acesso**: Apenas para usuários logados
- **Funcionalidades**:
  - Visualizar dados atuais do usuário
  - Editar informações pessoais (nome, data de nascimento, endereço, gênero, telefone, email, senha)
  - Atualizar perfil
  - Fazer logout

### 2. Servlet de Atualização de Perfil
- **Arquivo**: `src/java/Controller/AtualizarPerfilServlet.java`
- **URL**: `/AtualizarPerfil`
- **Funcionalidades**:
  - Receber dados do formulário de edição
  - Validar se o usuário está logado
  - Atualizar dados no banco de dados
  - Atualizar sessão do usuário
  - Exibir mensagens de sucesso ou erro

### 3. Método de Atualização no DAO
- **Arquivo**: `src/java/Model/PessoaDAO.java`
- **Método**: `atualizarPessoa(Pessoa pessoa, String emailOriginal)`
- **Funcionalidades**:
  - Executar query UPDATE no banco de dados
  - Atualizar todos os campos da tabela pessoa

### 4. Servlet de Logout
- **Arquivo**: `src/java/Controller/LogoutServlet.java`
- **URL**: `/Logout`
- **Funcionalidades**:
  - Invalidar sessão do usuário
  - Redirecionar para página inicial

### 5. Link na Página Inicial
- **Arquivo**: `web/Home.html`
- **Modificação**: Card "Minha Conta" agora é um link clicável
- **Destino**: `gerenciarPerfil.jsp`

## Como Usar

### 1. Acesso ao Sistema
1. Acesse a página inicial (`Home.html`)
2. Clique no card "Minha Conta" ou acesse diretamente `/gerenciarPerfil.jsp`
3. Se não estiver logado, será redirecionado para a página de login

### 2. Login
1. Acesse `/Login.jsp`
2. Digite seu email e senha
3. Após login bem-sucedido, será redirecionado para a página inicial

### 3. Gerenciamento de Perfil
1. Na página de gerenciamento de perfil, você verá:
   - Seus dados atuais preenchidos no formulário
   - Uma mensagem de boas-vindas com seu nome
2. Edite os campos desejados
3. Clique em "Atualizar Perfil" para salvar as mudanças
4. Uma mensagem de sucesso será exibida

### 4. Logout
1. Na página de gerenciamento de perfil, clique em "Sair"
2. Sua sessão será encerrada
3. Você será redirecionado para a página inicial

## Estrutura do Banco de Dados

A tabela `pessoa` deve conter os seguintes campos:
- `nome` (VARCHAR)
- `data_nascimento` (DATE)
- `endereco` (VARCHAR)
- `genero` (VARCHAR)
- `telefone` (VARCHAR)
- `email` (VARCHAR) - chave primária
- `senha` (VARCHAR)

## Segurança

- Todas as páginas de gerenciamento de perfil requerem autenticação
- Se um usuário não logado tentar acessar, será redirecionado para o login
- A sessão é invalidada no logout
- Os dados são validados antes de serem salvos no banco

## Arquivos Criados/Modificados

### Novos Arquivos:
- `src/java/Controller/AtualizarPerfilServlet.java`
- `src/java/Controller/LogoutServlet.java`
- `web/gerenciarPerfil.jsp`
- `README_GERENCIAMENTO_PERFIL.md`

### Arquivos Modificados:
- `src/java/Model/PessoaDAO.java` - Adicionado método `atualizarPessoa()`
- `web/Home.html` - Card "Minha Conta" agora é um link

## Próximos Passos Sugeridos

1. Implementar validação de dados no frontend (JavaScript)
2. Adicionar confirmação de senha para mudanças de senha
3. Implementar upload de foto de perfil
4. Adicionar histórico de alterações
5. Implementar recuperação de senha
6. Adicionar notificações por email para mudanças importantes 