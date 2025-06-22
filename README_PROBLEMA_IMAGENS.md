# Problema com Imagens no Catálogo

## Descrição do Problema
Após executar "Clean and Build", as imagens dos livros não aparecem mais no catálogo, mesmo estando salvas no banco de dados.

## Possíveis Causas

### 1. Problema de Codificação UTF-8
- As imagens podem ter sido salvas com codificação incorreta no banco
- Caracteres especiais nos nomes das imagens podem estar corrompidos

### 2. Problema de Caminho das Imagens
- As imagens podem estar sendo referenciadas com caminhos incorretos
- Os arquivos de imagem podem não estar na pasta correta

### 3. Problema de Conexão com Banco
- A string de conexão pode não estar usando UTF-8
- Dados podem estar sendo lidos com codificação incorreta

## Soluções Implementadas

### 1. Atualização da String de Conexão
O `catalogoDAO.java` foi atualizado para usar:
```java
private final String url = "jdbc:mysql://localhost:3306/biblioteca?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
```

### 2. Configuração de Codificação no Servlet
O `catalogoServlet.java` já está configurado com:
```java
req.setCharacterEncoding("UTF-8");
resp.setContentType("text/html; charset=UTF-8");
```

### 3. Filtro de Codificação no web.xml
Já existe um filtro configurado para forçar UTF-8 em todas as requisições.

## Passos para Diagnosticar e Resolver

### Passo 1: Verificar o Banco de Dados
Execute o script `verificar_imagens.sql` no MySQL para diagnosticar:

```sql
-- Conectar ao MySQL
mysql -u root -p

-- Executar o script
source verificar_imagens.sql;
```

### Passo 2: Verificar a Pasta de Imagens
Certifique-se de que:
1. A pasta `web/imagens/` existe
2. As imagens estão fisicamente na pasta
3. Os nomes das imagens no banco correspondem aos arquivos

### Passo 3: Verificar os Logs
Após fazer "Clean and Build", verifique:
1. Os logs do console do NetBeans
2. Os logs do servidor Tomcat
3. Os logs do `catalogoServlet` (que mostram informações sobre as imagens)

### Passo 4: Testar a Conexão
Verifique se o banco está acessível e se os dados estão sendo lidos corretamente.

## Comandos Úteis

### Verificar Estrutura da Tabela
```sql
DESCRIBE livro;
```

### Verificar Dados das Imagens
```sql
SELECT id, titulo, imagem FROM livro WHERE imagem IS NOT NULL;
```

### Contar Livros com Imagens
```sql
SELECT COUNT(*) as total, COUNT(imagem) as com_imagem FROM livro;
```

### Limpar Dados Corrompidos (se necessário)
```sql
UPDATE livro SET imagem = NULL WHERE imagem = '';
```

## Estrutura Esperada

### Pasta de Imagens
```
web/
├── imagens/
│   ├── livro1.jpg
│   ├── livro2.png
│   └── default_book.png
```

### Dados no Banco
```sql
-- Exemplo de dados corretos
INSERT INTO livro (titulo, autor, ano, genero, imagem) 
VALUES ('O Senhor dos Anéis', 'J.R.R. Tolkien', '1954-01-01', 'Fantasia', 'senhor_aneis.jpg');
```

## Se o Problema Persistir

1. **Verifique os logs**: Procure por erros relacionados a imagens
2. **Teste manualmente**: Acesse diretamente uma URL de imagem
3. **Verifique permissões**: Certifique-se de que o servidor pode acessar a pasta de imagens
4. **Reinicie o servidor**: Às vezes é necessário reiniciar o Tomcat após mudanças

## Contato
Se o problema persistir após seguir estes passos, verifique:
- Os logs do console
- A estrutura da pasta de imagens
- Os dados no banco de dados 