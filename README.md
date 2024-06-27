## Projeto final disciplina de Backend - SATC 2024

API REST para Processamento de Requisições HTTP

### Descrição

Esta API REST tem como objetivo processar requisições HTTP de um cliente e fornecer respostas adequadas.

A API deve oferecer funcionalidades básicas para gerenciar dados e realizar operações em um sistema, expondo endpoints para diferentes tipos de requisições (GET, POST, PUT, DELETE).


#### Link da imagem:
```
https://hub.docker.com/repository/docker/iurilima/projeto_final_backend_satc-mudancaclimatica/general
```

### Instruções para execução do docker
Para execução do compose.yaml o usuário deve possuir o docker compose instalado na máquina. Instruções para a instalação do mesmo pode ser encontrado no link abaixo:

Link : https://docs.docker.com/compose/install/

Após instalação do docker compose, basta executar o comando abaixo para rodar os containers da aplicação:

```docker compose up```


### Rotas Disponíveis

1. **Info do Projeto**

    - **GET** `/info`
        - Retorna informações sobre o projeto.


2. **Gerenciamento de Usuários**

    - **POST** `/user/signup`
        - Registra um novo usuário.
        - Exemplo de requisição:
          ```json
          {
            "username": "exampleUser",
            "password": "password123"
          }
          ```
        - Exemplo de resposta:
          ```json
          {
            "id": "1",
            "username": "exampleUser"
          }
          ```

    - **POST** `/user/signin`
   
        - Autentica o usuário e gera um token JWT.
      
        - Exemplo de requisição:
      
          ```json
          {
            "username": "exampleUser",
            "password": "password123"
          }
          ```
          
        - Exemplo de resposta:
      
          ```
          eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlVXNlciJ9.HBnZLMEFwz9rf6JqfN6YSeQc2sVkiSfRzBqy6XjVv0k
          ```

    - **DELETE** `/user/{id}`
   
        - Deleta um usuário pelo ID.
      
        - Exemplo de requisição: `/user/1`


3. **Dados Meteorológicos**

    - **GET** `/weather`
        - Retorna dados meteorológicos entre datas específicas para uma latitude e longitude.
        - Exemplo de requisição: `/weather?latitude=-23.5505&longitude=-46.6333&startDate=2024-01-01&endDate=2024-06-30`

    - **GET** `/weather/page-weather-data`
        - Retorna dados meteorológicos paginados entre datas específicas para uma latitude e longitude.
        - Exemplo de requisição: `/weather/page-weather-data?latitude=-23.5505&longitude=-46.6333&startDate=2024-01-01&endDate=2024-06-30&offset=0&limit=10`

    - **POST** `/weather`
        - Cria um novo registro meteorológico.
      
        - Exemplo de requisição:
      
          ```json
          {
            "longitude": "-46.6333",
            "latitude": "-23.5505",
            "recordDate": "2024-06-26T10:00:00",
            "carbonMonoxideMean": 0.5,
            "ozone": 20.1,
            "uvIndex": 7.2
          }
          ```

    - **PUT** `/weather/{id}`
        - Atualiza um registro meteorológico existente pelo ID.
      
        - Exemplo de requisição: `/weather/1`
      
          ```json
          {
            "longitude": "-46.6333",
            "latitude": "-23.5505",
            "recordDate": "2024-06-26T10:00:00",
            "carbonMonoxideMean": 0.6,
            "ozone": 21.0,
            "uvIndex": 7.5
          }
          ```

    - **DELETE** `/weather/{id}`
   
        - Deleta um registro meteorológico pelo ID.
      
        - Exemplo de requisição: `/weather/1`


### Autenticação e Segurança

- A API utiliza autenticação via token JWT para proteger as rotas de usuário e dados meteorológicos.
- A proteção CSRF está desativada para facilitar a integração com clientes externos.

O token JWT deve ser passado através do header no seguinte formato:
```
 Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0ZSIsInN1YiI6Iml1cmltYXJxdWVzQGhvdG1haWwuY29tIiwicm9sZSI6IkFETUlOIiwiaWF0IjoxNzE5MzYyOTAzLCJleHAiOjE3NzkzNjI5MDN9.dpKFuKAvP8G6BGyW4DgC3AjlgbpZ24aHLCKKHqmnBNXvGbcE1-5JOg_F1J7-dHTNU3pq0SuRWLvcRbxjZAGZAw
```


### Tecnologias Utilizadas

- Spring Boot
- Spring Security
- Spring Data MongoDB

### Conclusão

Esta API oferece funcionalidades para registro de usuários, gerenciamento de dados meteorológicos e autenticação segura por tokens JWT. Para mais detalhes sobre os parâmetros de requisição e respostas, consulte as descrições acima de cada endpoint específico.
