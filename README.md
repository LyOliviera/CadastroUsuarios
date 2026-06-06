# Cadastro de Usuários

Sistema desenvolvido em Java para gerenciamento e persistência de usuários.

## Pré-requisitos

### 1. Configuração do Banco de Dados

Para que o projeto funcione corretamente, é necessário configurar uma conexão com um banco de dados MySQL.

Na raiz do projeto, crie um arquivo chamado **`db.properties`** e adicione as informações de conexão:

```properties
dbhost=seu_host_aqui
dbporta=sua_porta_aqui
database=seu_banco_aqui
dbuser=seu_usuario_aqui
dbsenha=sua_senha_aqui
```

---

### 2. Criação da Tabela

Execute o comando SQL abaixo em seu gerenciador de banco de dados (como MySQL Workbench, DBeaver ou similar) para criar a tabela necessária:

```sql
CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    dtnascimento DATE,
    telefone BIGINT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    dtlimite DATE,
    dtalter TIMESTAMP,
    dtcriado TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

### 3. Executando as Operações

Para testar e executar as funcionalidades do sistema, abra e execute as seguintes classes em sua IDE (por exemplo, IntelliJ IDEA):

#### `RodarInsertOuUpdate.java`

Responsável por salvar um usuário:

* Cadastra um novo usuário; ou
* Atualiza um usuário já existente.

#### `RodarBuscaPorTodos.java`

Responsável por listar todos os usuários cadastrados no banco de dados.

#### `RodarBuscaPorId.java`

Responsável por buscar um usuário específico através do seu ID.

#### `RodarDelete.java`

Responsável por excluir um usuário do banco de dados.

---

## Funcionalidades

* Cadastro de usuários
* Atualização de usuários
* Busca por ID
* Listagem de usuários
* Exclusão de usuários
* Persistência de dados em MySQL

## Validações do Sistema

O sistema realiza validações antes de salvar os dados no banco de dados:

* Username obrigatório e único e inalterável.
* Nome completo obrigatório (nome e sobrenome).
* Validação completa de CPF, incluindo dígitos verificadores e duplicidade de ativos).
* E-mail válido e único para ativos.
* Senha com no mínimo 8 caracteres, contendo letras maiúsculas, minúsculas e números.
* Codificação da senha em Base64 antes do armazenamento.
* Data de nascimento não pode ser futura.
* Telefone obrigatório.
* Validação da existência do usuário antes da exclusão.

