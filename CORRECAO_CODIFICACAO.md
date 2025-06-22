# Correção de Problemas de Codificação UTF-8

## Problema Identificado
O endereço "Vereador Geraldo Bernardini.Boa EsperanÃ§a,434" está com caracteres corrompidos devido a problemas de codificação UTF-8.

## Soluções Implementadas

### 1. Configuração do Web.xml ✅
- Adicionado filtro de codificação UTF-8
- Configurado para forçar encoding em todas as requisições

### 2. Configuração do Banco de Dados ✅
- Atualizada string de conexão com parâmetros UTF-8
- Adicionado `useUnicode=true&characterEncoding=UTF-8&useSSL=false`

### 3. Configuração dos Servlets ✅
- Adicionado `request.setCharacterEncoding("UTF-8")`
- Adicionado `response.setCharacterEncoding("UTF-8")`
- Configurado `Content-Type` com charset UTF-8

### 4. Configuração da Página JSP ✅
- Já estava configurada com `charset=UTF-8` e `pageEncoding=UTF-8`

## Passos para Aplicar as Correções

### 1. Reiniciar o Servidor
Após as alterações, reinicie o servidor Tomcat/GlassFish para aplicar as configurações.

### 2. Executar Script SQL
Execute o script `fix_encoding.sql` no seu banco MySQL:

```sql
-- Configurar o banco para UTF-8
ALTER DATABASE biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Configurar a tabela pessoa
ALTER TABLE pessoa CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Corrigir Dados Existentes
Se houver dados já corrompidos, execute:

```sql
-- Corrigir caracteres específicos
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã§', 'ç') WHERE endereco LIKE '%Ã§%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã£', 'ã') WHERE endereco LIKE '%Ã£%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã£o', 'ão') WHERE endereco LIKE '%Ã£o%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã¡', 'á') WHERE endereco LIKE '%Ã¡%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã©', 'é') WHERE endereco LIKE '%Ã©%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã­', 'í') WHERE endereco LIKE '%Ã­%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã³', 'ó') WHERE endereco LIKE '%Ã³%';
UPDATE pessoa SET endereco = REPLACE(endereco, 'Ãº', 'ú') WHERE endereco LIKE '%Ãº%';
```

### 4. Verificar Configuração do MySQL
No arquivo `my.ini` ou `my.cnf` do MySQL, adicione:

```ini
[client]
default-character-set = utf8mb4

[mysql]
default-character-set = utf8mb4

[mysqld]
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
```

## Teste da Correção

1. **Acesse a página de gerenciamento de perfil**
2. **Edite o endereço** para: "Vereador Geraldo Bernardini, Boa Esperança, 434"
3. **Salve as alterações**
4. **Verifique se os caracteres especiais** (ç, ã, etc.) aparecem corretamente

## Caracteres Comuns que Podem Estar Corrompidos

| Corrompido | Correto |
|------------|---------|
| Ã§ | ç |
| Ã£ | ã |
| Ã£o | ão |
| Ã¡ | á |
| Ã© | é |
| Ã­ | í |
| Ã³ | ó |
| Ãº | ú |

## Verificação Final

Após aplicar todas as correções, o endereço deve aparecer como:
**"Vereador Geraldo Bernardini, Boa Esperança, 434"**

## Se o Problema Persistir

1. Verifique se o navegador está configurado para UTF-8
2. Limpe o cache do navegador
3. Verifique se o arquivo está sendo salvo em UTF-8 no editor
4. Confirme se o servidor está configurado para UTF-8 