-- Script para criar a tabela de locações
-- Execute este script no seu banco MySQL

-- Criar tabela de locações
CREATE TABLE IF NOT EXISTS locacao (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Criar índices para melhor performance
CREATE INDEX idx_email_usuario ON locacao(email_usuario);
CREATE INDEX idx_titulo_livro ON locacao(titulo_livro);
CREATE INDEX idx_status ON locacao(status);
CREATE INDEX idx_data_fim ON locacao(data_fim);

-- Verificar se a tabela foi criada corretamente
DESCRIBE locacao;

-- Mostrar estrutura da tabela
SHOW CREATE TABLE locacao; 