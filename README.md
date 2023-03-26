# Web-Service-JPA-h2
# Description:
- O projeto usa o framework `Spring Boot` com as dependências: `Spring Web`, `JPA`, `H2`.
- A Classe `WebServiceApplication` é responsável por levantar a aplicação
- A Classe `UserRepository` é responsável por fornecer a interação com o banco de dados em memoria `h2` através da `JPA`.
- A Classe `User` representa um usuário com os campos `name` e `email`. Além disso, utiliza o `hibernate e JPA` para ser mapeada para uma tabela no banco de dados `h2`.
  
- A Classe `UserController` é responsável por implementar os endpoints.
# Tutorial: Como rodar a imagem do projeto a partir de um contêiner do Docker Hub

## Pré-requisitos:
- [Docker](https://www.docker.com/) instalado e configurado em seu ambiente.

## Passo 1: Pesquise a imagem desejada no Docker Hub
- Veja a imagem desse projeto no meu repositório do [Docker Hub](https://hub.docker.com/r/leandroadal/web-service-jpa-h2).

## Passo 2: Baixe a imagem Docker
- Abra um terminal ou prompt de comando e execute o seguinte comando:
  
    ```
    docker pull leandroadal/web-service-jpa-h2   
    ```

## Passo 3: Execute o container
- Após o download da imagem, execute o seguinte comando para iniciar o container:
    ```
    docker run -p 8080:8080 leandroadal/web-service-jpa-h2:latest
    ```

- Este comando irá iniciar o container e redirecionar as solicitações HTTP para a porta 8080 do host.

## Passo 4: Acesse a aplicação
- Abra um navegador web e acesse `http://localhost:8080/` para acessar a aplicação.


# Tutorial de como utilizar os endpoints da aplicação

A seguir, serão listados os endpoints disponíveis neste web service, bem como as informações necessárias para realizar as requisições.

## Endpoints da aplicação
A aplicação possui os seguintes endpoints:

* **GET /users**: retorna a lista de todos os clientes cadastrados.

* **GET /users/{id}**: retorna os detalhes do cliente com o ID especificado.
* **POST /users**: cria um novo cliente.
* **PUT /users/{id}**: atualiza todos os dados do cliente com o ID especificado.
* **PATCH /users/{id}**: atualiza parcialmente o cliente com o ID especificado.
* **DELETE /users/{id}**: remove o cliente com o ID especificado.
* **OPTIONS /users**: retorna os métodos HTTP permitidos para a rota.
* **HEAD /users/{id}**: verifica se o cliente com o ID especificado existe.

&nbsp;

Para testar cada um desses endpoints, siga as instruções abaixo:

&nbsp;

## Pré-requisitos:

* Docker instalado e configurado em seu ambiente.
* Imagem Docker da aplicação baixada e em execução.
  
## Forma 1: Terminal
### Passo 1: Acessar o terminal

* Abra um terminal ou prompt de comando e execute o seguinte comando:

    ```
    docker exec -it <nome-do-container> /bin/bash
    ```

- Troque <\nome-do-container> pelo nome que o contêiner ganhou no docker
  
### Passo 2: Testar os endpoints
* Com o terminal aberto, execute os comandos abaixo para testar cada endpoint:
#### GET /users

```
curl -X GET http://localhost:8080/users
```
#### GET /users/{id}

```
curl -X GET http://localhost:8080/users/1
```

Substitua 1 pelo ID do cliente que deseja consultar.

#### POST /users
```
curl -X POST -H "Authorization: 123" -H 'Content-Type: application/json' -d '{"name": "João da Silva","email": "joao.silva@arapiraca.ufal.br"}' http://localhost:8080/users
```
#### PUT /users/{id}
```
curl -X PUT -H "Authorization: 123" -H "Content-Type: application/json" -d '{"name":"João da Silva Santos"}' http://localhost:8080/users/1
```
#### PATCH /users/{id}
```
curl -X PATCH -H "Authorization: 123" -H "Content-Type: application/json-patch+json" -d '{"email": "João.santos@arapiraca.ufal.br"}' http://localhost:8080/users/1
```
#### DELETE /users/{id}
```
curl -X DELETE -H "Authorization: 123" http://localhost:8080/users/1
```
#### OPTIONS /users
```
curl -X OPTIONS -H "Authorization: 123" http://localhost:8080/users
```
- Como OPTIONS retorna no cabeçalho uma lista das endpoints disponíveis naquele caminho, não há resposta no terminal.
#### HEAD /users/{id}
```
curl -X HEAD -H "Authorization: 123" http://localhost:8080/users/1
```
- Como HEAD retorna no cabeçalho o Usuário se ele existir, não há resposta no terminal.

## Forma 2: POSTMAN

O [POSTMAN](https://www.postman.com/) oferece uma interface para fazer as requisições http mais fácil e pratica.

### Siga esses passos para realizar uma requisição:

- Criar uma requisição Http `ctrl+n` e então clicar em `Http Request`

### Exemplo para cada endpoint:

#### GET /users

- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users
- Selecione o método HTTP `GET`
 - Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.

#### GET /users/{id}
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users/`id procurado`
- Selecione o método HTTP `GET`
 - Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.

Substitua pelo ID do cliente que deseja consultar.

#### POST /users

- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users
- Selecione o método HTTP `POST`
- Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
 - Na aba "body", marque `raw`, em seguida mude o tipo de `Text` para `JSON` e adicione um conforme código abaixo:
```
{
    "name": "João da Silva",
    "email": "joao.silva@arapiraca.ufal.br"
}
```
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.

#### PUT /users/{id}
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users/`id`
- Selecione o método HTTP `PUT`
- Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
 - Na aba "body", marque `raw`, em seguida mude o tipo de `Text` para `JSON` e adicione o código abaixo:
```
{
    "name": "João da Silva Santos"
}
```
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.
- Note que o `email` ficará nulo, pois não foi declaro no PUT.
#### PATCH /users/{id}
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users/`id`
- Selecione o método HTTP `PATCH`
- Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
 - Na aba "body", marque `raw`, em seguida mude o tipo de `Text` para `JSON` e adicione o código abaixo:
```
{
    "email": "João.Silva@arapiraca.ufal.br"
}
```
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.
- Note que o `name` não ficou nulo, pois o PATCH so atualiza os campos informados.
#### DELETE /users/{id}
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users/`id`
- Selecione o método HTTP `DELETE`
 - Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.
#### OPTIONS /users
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users
- Selecione o método HTTP `OPTIONS`
 - Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.
- Como `OPTIONS` retorna no cabeçalho uma lista das endpoints disponíveis voce deve entra na aba `Headers` da resposta para visualiza-la no campo `allow`.
#### HEAD /users/{id}
- Clique no botão "New" para criar uma nova requisição
- Na URL coloque http://localhost:8080/users/`id`
- Selecione o método HTTP `HEAD`
 - Na aba "Headers", adicione a Key `Authorization` e o Value `123`.
- Clique no botão `Send` para enviar a requisição e visualizar a resposta da API.
- Como `HEAD` retorna no cabeçalho o Usuário se ele existir, não há resposta no terminal voce deve entra na aba `Headers` da resposta para visualiza-la no campo `id`.
