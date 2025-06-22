# Sistema de Locação - Estrutura Atualizada

## Mudanças Implementadas

### 🔄 **Nova Estrutura da Tabela de Locação**

A tabela `locacao` foi atualizada para usar **chaves estrangeiras** em vez de strings:

#### **Estrutura Anterior:**
```sql
CREATE TABLE locacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email_usuario VARCHAR(255),
    titulo_livro VARCHAR(255),
    autor_livro VARCHAR(255),
    data_inicio DATE,
    data_fim DATE,
    status VARCHAR(20),
    data_devolucao DATE
);
```

#### **Nova Estrutura:**
```sql
CREATE TABLE locacao (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_pessoa INT(11) NOT NULL,
    id_livro INT(11) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    data_locacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ativa',
    PRIMARY KEY (id),
    
    CONSTRAINT fk_locacao_pessoa
        FOREIGN KEY (id_pessoa)
        REFERENCES pessoa (id)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_locacao_livro
        FOREIGN KEY (id_livro)
        REFERENCES livro (id)
        ON DELETE CASCADE
);
```

### 📊 **Vantagens da Nova Estrutura**

1. **Integridade Referencial**: Garante que locações só existam para pessoas e livros válidos
2. **Performance**: Consultas mais rápidas usando IDs em vez de strings
3. **Consistência**: Evita duplicação de dados e inconsistências
4. **Manutenibilidade**: Mudanças em pessoas/livros refletem automaticamente nas locações

### 🔧 **Arquivos Atualizados**

#### **Modelos:**
- `src/java/Model/Locacao.java` - Agora usa `idPessoa` e `idLivro`
- `src/java/Model/Pessoa.java` - Adicionado campo `id`
- `src/java/Model/Livro.java` - Novo modelo criado

#### **DAOs:**
- `src/java/Model/LocacaoDAO.java` - Atualizado para trabalhar com IDs
- `src/java/Model/PessoaDAO.java` - Adicionado método `buscarPorEmail()`
- `src/java/Model/LivroDAO.java` - Adicionados métodos `buscarPorId()` e `buscarPorTitulo()`

#### **Servlets:**
- `src/java/Controller/LocacaoServlet.java` - Trabalha com IDs
- `src/java/Controller/MeusLivrosServlet.java` - Usa `LocacaoDTO`
- `src/java/Controller/catalogoServlet.java` - Inclui ID do livro

#### **Páginas:**
- `web/Locacao.jsp` - Atualizada para buscar informações do livro por ID

### 🗄️ **Estrutura do Banco de Dados**

#### **Tabela `pessoa`:**
```sql
CREATE TABLE pessoa (
    id INT(11) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    data_nascimento VARCHAR(10),
    endereco VARCHAR(255),
    genero VARCHAR(20),
    telefone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

#### **Tabela `livro`:**
```sql
CREATE TABLE livro (
    id INT(11) NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    ano VARCHAR(4),
    genero VARCHAR(100),
    imagem VARCHAR(500),
    PRIMARY KEY (id)
);
```

#### **Tabela `locacao`:**
```sql
CREATE TABLE locacao (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_pessoa INT(11) NOT NULL,
    id_livro INT(11) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    data_locacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ativa',
    PRIMARY KEY (id),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON DELETE CASCADE,
    FOREIGN KEY (id_livro) REFERENCES livro (id) ON DELETE CASCADE
);
```

### 📋 **Como Aplicar as Mudanças**

#### **1. Executar Script SQL:**
```sql
-- Execute o arquivo criar_tabela_locacao_atualizada.sql
```

#### **2. Migrar Dados Existentes (se necessário):**
```sql
-- Se você já tem dados, pode ser necessário migrar:
-- 1. Adicionar campo id às tabelas pessoa e livro
-- 2. Migrar dados da tabela locacao antiga para a nova estrutura
```

#### **3. Reiniciar Servidor:**
- Reinicie o servidor após as alterações

#### **4. Testar Funcionalidades:**
- Faça login
- Acesse o catálogo
- Tente fazer uma locação
- Verifique "Meus Livros"

### 🔍 **Principais Mudanças no Código**

#### **Antes:**
```java
// Locação usando strings
Locacao locacao = new Locacao();
locacao.setEmailUsuario("usuario@email.com");
locacao.setTituloLivro("Dom Casmurro");
locacao.setAutorLivro("Machado de Assis");
```

#### **Depois:**
```java
// Locação usando IDs
Locacao locacao = new Locacao();
locacao.setIdPessoa(1);  // ID da pessoa
locacao.setIdLivro(5);   // ID do livro
```

### 📊 **Classe LocacaoDTO**

Criada para retornar dados completos das locações:

```java
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
    // getters e setters...
}
```

### ✅ **Status dos Status**

Os status das locações agora usam valores em minúsculas:
- `"ativa"` - Livro está locado
- `"devolvida"` - Livro foi devolvido
- `"atrasada"` - Data de devolução passou

### 🚀 **Benefícios da Atualização**

1. **Melhor Performance**: Consultas mais rápidas
2. **Integridade de Dados**: Relacionamentos garantidos
3. **Escalabilidade**: Sistema mais robusto
4. **Manutenibilidade**: Código mais limpo e organizado
5. **Consistência**: Dados sempre sincronizados

### 📝 **Próximos Passos**

1. Execute o script SQL
2. Teste todas as funcionalidades
3. Verifique se os dados existentes foram migrados corretamente
4. Monitore o desempenho do sistema

A nova estrutura torna o sistema mais robusto, eficiente e fácil de manter! 