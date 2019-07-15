CREATE TABLE usuario(
	id INT NOT NULL PRIMARY KEY,
	nome VARCHAR(100),
	atualizado BOOLEAN NOT NULL,
	deletado BOOLEAN NOT NULL
);
CREATE TABLE key (
    id INT NOT NULL PRIMARY KEY,
    codigoEntidade INT
);
