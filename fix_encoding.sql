-- Script para corrigir problemas de codificação UTF-8 no banco de dados
-- Execute este script no seu banco MySQL

-- 1. Verificar a configuração atual do banco
SHOW VARIABLES LIKE 'character_set%';
SHOW VARIABLES LIKE 'collation%';

-- 2. Configurar o banco para usar UTF-8
ALTER DATABASE biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 3. Configurar a tabela pessoa para usar UTF-8
ALTER TABLE pessoa CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 4. Verificar se a tabela foi convertida corretamente
SHOW CREATE TABLE pessoa;

-- 5. Atualizar configurações globais (opcional - requer privilégios de administrador)
-- SET GLOBAL character_set_server = utf8mb4;
-- SET GLOBAL collation_server = utf8mb4_unicode_ci;

-- 6. Verificar se há dados corrompidos e corrigir (exemplo)
-- UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã§', 'ç') WHERE endereco LIKE '%Ã§%';
-- UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã£', 'ã') WHERE endereco LIKE '%Ã£%';
-- UPDATE pessoa SET endereco = REPLACE(endereco, 'Ã£o', 'ão') WHERE endereco LIKE '%Ã£o%';

-- 7. Verificar dados após correção
SELECT nome, endereco FROM pessoa LIMIT 5; 