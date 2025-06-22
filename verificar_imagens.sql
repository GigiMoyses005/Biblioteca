-- Script para verificar e corrigir imagens no banco de dados
-- Execute este script no MySQL para diagnosticar problemas com imagens

USE biblioteca;

-- 1. Verificar a estrutura da tabela livro
DESCRIBE livro;

-- 2. Verificar todos os livros e suas imagens
SELECT id, titulo, autor, imagem FROM livro;

-- 3. Verificar livros que têm imagens
SELECT id, titulo, autor, imagem 
FROM livro 
WHERE imagem IS NOT NULL AND imagem != '';

-- 4. Verificar livros que NÃO têm imagens
SELECT id, titulo, autor, imagem 
FROM livro 
WHERE imagem IS NULL OR imagem = '';

-- 5. Contar quantos livros têm imagens
SELECT 
    COUNT(*) as total_livros,
    COUNT(imagem) as livros_com_imagem,
    COUNT(*) - COUNT(imagem) as livros_sem_imagem
FROM livro;

-- 6. Se necessário, atualizar livros sem imagem para ter uma imagem padrão
-- Descomente as linhas abaixo se quiser adicionar uma imagem padrão
/*
UPDATE livro 
SET imagem = 'default_book.png' 
WHERE imagem IS NULL OR imagem = '';
*/

-- 7. Verificar se há caracteres especiais ou problemas de codificação nas imagens
SELECT id, titulo, imagem, 
       LENGTH(imagem) as tamanho_nome,
       HEX(imagem) as imagem_hex
FROM livro 
WHERE imagem IS NOT NULL AND imagem != '';

-- 8. Limpar nomes de imagens com caracteres especiais (se necessário)
-- Descomente se houver problemas de codificação
/*
UPDATE livro 
SET imagem = REPLACE(imagem, 'Ã¡', 'á'),
    imagem = REPLACE(imagem, 'Ã£', 'ã'),
    imagem = REPLACE(imagem, 'Ã§', 'ç'),
    imagem = REPLACE(imagem, 'Ã©', 'é'),
    imagem = REPLACE(imagem, 'Ã­', 'í'),
    imagem = REPLACE(imagem, 'Ã³', 'ó'),
    imagem = REPLACE(imagem, 'Ãº', 'ú')
WHERE imagem IS NOT NULL;
*/ 