# ForumHub API

O **ForumHub API** é uma aplicação backend construída com **Java** e **Spring Boot**, que fornece uma API segura e robusta para um fórum. A aplicação permite autenticação via **JWT**, criação e gerenciamento de tópicos, além de controles de acesso baseados em permissões do usuário.

## Funcionalidades

* **Autenticação com JWT**
  O usuário realiza login via email e senha e recebe um token JWT para autenticação nas próximas requisições.

* **Criação de Tópicos**
  Usuários autenticados podem criar tópicos informando título, mensagem e curso.

* **Listagem de Tópicos Ativos**
  Exibe apenas os tópicos com estado ativo (`estadoTopico = true`) de forma paginada.

* **Consulta por ID**
  Retorna os detalhes de um tópico específico pelo seu ID.

* **Atualização de Tópicos**
  Permite ao autor do tópico editar o conteúdo.

* **Exclusão Lógica**
  O autor pode desativar (ocultar) seus próprios tópicos.

* **Exclusão Permanente**
  Recurso reservado para testes ou manutenção do banco. Remove definitivamente um tópico.

## Tecnologias Utilizadas

* **Java 17**
* **Spring Boot**
* **Spring Web**
* **Spring Security + JWT**
* **Spring Data JPA**
* **MySQL**
* **Maven**

## Como Usar

### 1. Clone o repositório:

```bash
git clone https://github.com/ghscesario/ORACLE-ONE-Challenge-ForumHub.git
```

### 2. Configure o banco de dados no `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
spring.datasource.username=seuusuario
spring.datasource.password=suasenha
```

OBS: É necessário a criação prévia do banco de dados de forma manual com o nome: forumHub

---

## Configuração da Secret para JWT

A aplicação utiliza uma **chave secreta** para assinar os tokens JWT. Essa chave é configurada no arquivo `application.properties` através da propriedade:

```properties
api.security.token.secret=${JWT_SECRET:12345678}
```

* `JWT_SECRET` é uma variável de ambiente que pode ser configurada no sistema operacional para manter a chave secreta segura e fora do código-fonte.
* Caso a variável de ambiente não esteja configurada, a aplicação usará o valor padrão `"12345678"`, que **não é recomendado para produção**.

### Recomendações

* **Nunca deixe a chave secreta padrão em ambientes de produção.**
* Configure a variável de ambiente `JWT_SECRET` no servidor, por exemplo:

```bash
export JWT_SECRET=sua_chave_super_secreta_aqui
```

* Isso aumenta a segurança da aplicação, evitando exposição do segredo no código-fonte.

---

### 3. Execute o projeto:

```bash
./mvnw spring-boot:run
```

---

## ⚠️ Criação de Usuários

Atualmente, **os usuários devem ser criados diretamente no banco de dados**, com login e senha criptografada usando `BCryptPasswordEncoder`, como é padrão no Spring Security.

Exemplo de inserção via SQL:

```sql
INSERT INTO usuarios (login, senha) VALUES ('admin@email.com', '{bcrypt}senha_criptografada');
```

> Recomenda-se utilizar um gerador de hash BCrypt para inserir a senha corretamente.

---

## 🔐 Autenticação com JWT

### 1. Endpoint de Login

**POST** `/login`

**Requisição:**

```json
{
  "login": "admin@email.com",
  "senha": "123456"
}
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Você deve usar o token recebido no cabeçalho de **todas as requisições protegidas**:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## Endpoints da API

| Método   | Endpoint                       | Descrição                                     |
| -------- | ------------------------------ | --------------------------------------------- |
| `POST`   | `/login`                       | Autentica usuário e retorna token JWT         |
| `POST`   | `/topico/insert`               | Cria novo tópico (requer token)               |
| `GET`    | `/topico`                      | Lista tópicos ativos (paginado, requer token) |
| `GET`    | `/topico/{id}`                 | Retorna tópico por ID (requer token)          |
| `PUT`    | `/topico/{id}`                 | Atualiza tópico (somente autor, requer token) |
| `DELETE` | `/topico/{id}`                 | Exclusão lógica (somente autor, requer token) |
| `DELETE` | `/topico/deletepermanent/{id}` | Exclusão permanente (uso em testes)           |

---

## Exemplo de Criação de Tópico

**POST** `/topico/insert`

**Corpo da requisição:**

```json
{
  "titulo": "Como configurar JWT?",
  "mensagem": "Estou com dificuldades em configurar JWT no Spring Security",
  "curso": "Java Backend"
}
```

> Necessário incluir o header:
> `Authorization: Bearer <seu_token_jwt>`

---

## Futuras Melhorias

* Cadastro de usuário via endpoint público (`/register`)
* Integração com Swagger para documentação interativa
* Comentários e respostas em tópicos
* Filtros por curso ou autor
