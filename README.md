# Plataforma de e-Commerce - Arquitetura com Microserviços

Este projeto é uma aplicação de e-commerce desenvolvida em arquitetura de microserviços com autenticação baseada em tokens JWT via Auth0. O sistema é responsável por gerenciar usuários, produtos e pedidos, além de permitir a integração com Kafka para comunicação assíncrona entre os serviços.

---

## 🏗️ Arquitetura

O projeto está dividido em 5 microserviços principais:

- **Eureka Server** – Serviço de descoberta.
- **Gateway** – Roteador central para os demais serviços.
- **User** – Gerenciamento de usuários.
- **Produto** – Gerenciamento de produtos e controle de estoque.
- **Pedido** – Criação e gerenciamento de pedidos.

---

## 🛠️ Tecnologias Utilizadas

- **Java**  
- **Spring Boot**  
- **Spring Security**  
- **Spring Cloud**  
- **Spring WebFlux**  
- **Spring Actuator**  
- **Hibernate / JPA**  
- **Lombok**  
- **MapStruct (Mapper)**  
- **Kafka**  
- **Auth0** (Autenticação baseada em JWT)  
- **Eureka Server**
- **Docker**
- **PostgreSQL**  

---

## 📡 Endpoint User

Cadastrar usuario.
- `POST /api/users`
```
{
  "login": "usuario@example.com",
  "password": "JBSWY3DPEHPK3PXP",
}
```
Gera o token.
- `POST /api/users/token`
```
{
  "login": "usuario@example.com",
  "password": "JBSWY3DPEHPK3PXP",
}
```
Pesquisa os usuarios pelo login.
- `GET /api/users/{login}`

Retornar todos os usuarios.
- `GET /api/users/loten`

## 📡 Endpoint Produto.
Cadastra novo produto.
- `POST /api/produtos`
```
{
  "codigo": "123456",
  "nome": "nome do produto",
  "descricao": "descrição do produto",
  "preco": 99.99,
  "quantidadeEstoque": 12,
  "categoria": "categoria do produto",
  "imagemUrl": "Url da imagem",
  "Ativo": true,
}
```

Cria um lista de produtos
- `GET /api/produtos/lote`
```
[ 
  {
    "codigo": "123456",
    "nome": "nome do produto",
    "descricao": "descrição do produto",
    "preco": 99.99,
    "quantidadeEstoque": 12,
    "categoria": "categoria do produto",
    "imagemUrl": "Url da imagem",
    "Ativo": true,
  },
  {
    "codigo": "123456",
    "nome": "nome do produto",
    "descricao": "descrição do produto",
    "preco": 99.99,
    "quantidadeEstoque": 12,
    "categoria": "categoria do produto",
    "imagemUrl": "Url da imagem",
    "Ativo": true,
  },
] 
```
Pesquisa o produto pelo codigo.
- `GET /api/produtos/{codigo}`

Retorna todos os produtos.
- `GET /api/produtos`

Atualiza o produto pelo codigo.
- `PUT /api/produtos/{codigo}`
```
{
  "codigo": "123456",
  "nome": "nome do produto",
  "descricao": "descrição do produto",
  "preco": 99.99,
  "quantidadeEstoque": 12,
  "categoria": "categoria do produto",
  "imagemUrl": "Url da imagem",
  "Ativo": true,
}
```

Deleta produto pelo codigo
- `DELETE /api/produtos/{codigo}`

## 📡 Endpoint Pedido.
Cadastra de um novo pedido.
- Ao realizar a criação do pedido, os dados do produto como codigo e quantidade são enviados ao Kafka, assim o micro serviço do produto consegue visualizar a dar baixa no estoque do mesmo.
- `POST /api/pedidos`
```
{
  "usuarioLogin": "teste.teste",
  {
    "codigoProduto": "123456",
    "preco": 99.99,
    "quantidade": 12,
  }
}
```
