-- Script para criar a tabela de locações com a nova estrutura
-- Execute este script no seu banco MySQL

-- Verificar se as tabelas pessoa e livro existem e têm campo id
-- Se não existirem, criar primeiro

-- Criar tabela pessoa se não existir (com campo id)
CREATE TABLE IF NOT EXISTS pessoa (
    id INT(11) NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    data_nascimento VARCHAR(10),
    endereco VARCHAR(255),
    genero VARCHAR(20),
    telefone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Criar tabela livro se não existir (com campo id)
CREATE TABLE IF NOT EXISTS livro (
    id INT(11) NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255),
    ano VARCHAR(4),
    genero VARCHAR(100),
    imagem VARCHAR(500),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Criar tabela de locações com a nova estrutura
CREATE TABLE IF NOT EXISTS locacao (
    id INT(11) NOT NULL AUTO_INCREMENT,
    id_pessoa INT(11) NOT NULL,
    id_livro INT(11) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    data_locacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ativa' COLLATE 'utf8mb4_general_ci',
    PRIMARY KEY (id) USING BTREE,
    
    CONSTRAINT fk_locacao_pessoa
        FOREIGN KEY (id_pessoa)
        REFERENCES pessoa (id)
        ON DELETE CASCADE,
    
    CONSTRAINT fk_locacao_livro
        FOREIGN KEY (id_livro)
        REFERENCES livro (id)
        ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT COLLATE='utf8mb4_general_ci';

-- Criar índices para melhor performance
CREATE INDEX idx_locacao_id_pessoa ON locacao(id_pessoa);
CREATE INDEX idx_locacao_id_livro ON locacao(id_livro);
CREATE INDEX idx_locacao_status ON locacao(status);
CREATE INDEX idx_locacao_data_fim ON locacao(data_fim);

-- Verificar se as tabelas foram criadas corretamente
DESCRIBE pessoa;
DESCRIBE livro;
DESCRIBE locacao;

-- Mostrar estrutura das tabelas
SHOW CREATE TABLE pessoa;
SHOW CREATE TABLE livro;
SHOW CREATE TABLE locacao;

-- Inserir alguns dados de exemplo (opcional)
-- INSERT INTO pessoa (nome, data_nascimento, endereco, genero, telefone, email, senha) 
-- VALUES ('João Silva', '1990-01-01', 'Rua A, 123', 'Masculino', '(11) 99999-9999', 'joao@email.com', '123456');

-- INSERT INTO livro (titulo, autor, ano, genero, imagem) 
-- VALUES ('Dom Casmurro', 'Machado de Assis', '1899', 'Romance', 'dom_casmurro.jpg'); 