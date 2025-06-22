# Sistema de Locação de Livros - Bookfy

## Funcionalidades Implementadas

### 1. **Sistema de Autenticação para Locação** ✅
- Apenas usuários logados podem fazer locações
- Verificação automática de sessão
- Redirecionamento para login se não autenticado

### 2. **Página de Locação Melhorada** ✅
- **Arquivo**: `web/Locacao.jsp`
- Interface moderna e responsiva
- Formulário com validação de datas
- Informações do usuário e livro preenchidas automaticamente

### 3. **Processamento de Locações** ✅
- **Servlet**: `LocacaoServlet.java` (`/ProcessarLocacao`)
- Validação de dados e datas
- Verificação de disponibilidade do livro
- Salvamento no banco de dados

### 4. **Sistema de "Meus Livros"** ✅
- **Servlet**: `MeusLivrosServlet.java` (`/MeusLivros`)
- Lista todas as locações do usuário
- Status das locações (Ativa, Devolvida, Atrasada)
- Botão para devolver livros

### 5. **Sistema de Devolução** ✅
- **Servlet**: `DevolverLivroServlet.java` (`/DevolverLivro`)
- Processamento de devoluções
- Atualização automática de status

### 6. **Verificação de Disponibilidade** ✅
- **DAO**: `LocacaoDAO.java`
- Verifica se o livro está disponível
- Atualização automática de status atrasado
- Controle de disponibilidade no catálogo

### 7. **Catálogo Atualizado** ✅
- **Servlet**: `catalogoServlet.java` atualizado
- Mostra status de disponibilidade de cada livro
- Botão "Alugar" apenas para livros disponíveis
- Informações do autor incluídas

## Estrutura do Banco de Dados

### Tabela `locacao`
```sql
CREATE TABLE locacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email_usuario VARCHAR(255) NOT NULL,
    titulo_livro VARCHAR(255) NOT NULL,
    autor_livro VARCHAR(255),
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status ENUM('ATIVA', 'DEVOLVIDA', 'ATRASADA') DEFAULT 'ATIVA',
    data_devolucao DATE NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (email_usuario) REFERENCES pessoa(email) ON DELETE CASCADE
);
```

## Como Usar o Sistema

### 1. **Fazer Login**
- Acesse `/Login.jsp`
- Faça login com suas credenciais

### 2. **Explorar o Catálogo**
- Acesse `/catalogo`
- Veja os livros disponíveis e indisponíveis
- Clique em "Alugar" para livros disponíveis

### 3. **Fazer uma Locação**
- Selecione um livro no catálogo
- Preencha as datas de início e devolução
- Confirme a locação

### 4. **Gerenciar Seus Livros**
- Acesse `/MeusLivros`
- Veja todas as suas locações
- Devolva livros quando necessário

### 5. **Gerenciar Perfil**
- Acesse `gerenciarPerfil.jsp`
- Atualize suas informações pessoais

## Fluxo de Locação

1. **Usuário acessa o catálogo** → `/catalogo`
2. **Seleciona um livro disponível** → Botão "Alugar"
3. **É redirecionado para locação** → `Locacao.jsp`
4. **Preenche datas** → Formulário de locação
5. **Confirma locação** → `ProcessarLocacao`
6. **Livro é salvo no banco** → Tabela `locacao`
7. **Pode ver em "Meus Livros"** → `/MeusLivros`

## Status das Locações

- **ATIVA**: Livro está locado e deve ser devolvido
- **DEVOLVIDA**: Livro foi devolvido
- **ATRASADA**: Data de devolução passou

## Validações Implementadas

### Frontend (JavaScript)
- Data de início não pode ser anterior a hoje
- Data de devolução deve ser posterior à data de início
- Campos obrigatórios

### Backend (Java)
- Verificação de autenticação
- Validação de datas
- Verificação de disponibilidade
- Validação de parâmetros

## Arquivos Criados/Modificados

### Novos Arquivos:
- `src/java/Model/Locacao.java` - Modelo de locação
- `src/java/Model/LocacaoDAO.java` - DAO para locações
- `src/java/Controller/LocacaoServlet.java` - Processamento de locações
- `src/java/Controller/MeusLivrosServlet.java` - Listagem de livros
- `src/java/Controller/DevolverLivroServlet.java` - Devolução de livros
- `web/Locacao.jsp` - Página de locação
- `criar_tabela_locacao.sql` - Script SQL
- `README_SISTEMA_LOCACAO.md` - Documentação

### Arquivos Modificados:
- `src/java/Controller/catalogoServlet.java` - Adicionada verificação de disponibilidade
- `web/Home.html` - Links atualizados para "Meus Livros"

## Configuração Necessária

### 1. Executar Script SQL
```sql
-- Execute o arquivo criar_tabela_locacao.sql no seu MySQL
```

### 2. Reiniciar Servidor
- Reinicie o servidor após as alterações

### 3. Testar Funcionalidades
- Faça login
- Acesse o catálogo
- Tente fazer uma locação
- Verifique "Meus Livros"

## Próximas Melhorias Sugeridas

1. **Sistema de Notificações**
   - Email para lembretes de devolução
   - Notificações de atraso

2. **Histórico de Locações**
   - Página com histórico completo
   - Estatísticas de leitura

3. **Sistema de Avaliações**
   - Avaliar livros após leitura
   - Comentários e reviews

4. **Renovação de Locações**
   - Extender prazo de locação
   - Verificar disponibilidade para renovação

5. **Fila de Espera**
   - Lista de espera para livros indisponíveis
   - Notificação quando disponível

6. **Relatórios**
   - Livros mais populares
   - Estatísticas de uso
   - Relatórios de atraso 