# e-Core hiring exercise

## Descrição

Todo o código desta API foi armazendo no meu repositório do Github https://github.com/raphaelrdsoares/e-core-hiring-exercise.

## Funcionalidades

Colocar aqui os endpoint e os critérios de aceite.

## Documentação

Quanto a questão de documentação, escolhi realizar as seguintes abordagens:

### Documento de arquitetura

Seguindo a boa prática recomendada pela Thoughtworks no TechRadar de utilizar diagramas como código ([diagram as code](https://www.thoughtworks.com/radar/techniques/diagrams-as-code)), elaborei um diagrama de arquitetura como código seguindo o padrão [modelo C4](<[https://](https://c4model.com/)>).

Este diagrama pode ser encontrado na pasta [/docs/c4-architecture-diagram.dsl](./docs/c4-architecture-diagram.dsl). Para visualizar o diagrama, basta copiar o código deste arquivo, colar campo de texto do site https://structurizr.com/dsl, e clicar em "Render".

### OpenAPI e Postman

Para documentar os endpoints criados, escolhi utilizar o swagger, fazendo uso a biblioteca `springdoc-openapi-ui` para gerar documentações automáticas.

Ao executar a aplicação, basta acessar a URL [/api/docs/swagger](<[https://](http://localhost:9000/api/docs/swagger)>) para visualizar a documentação dos endpoints utilizando o padrão visual do swagger, ou acessar a URL [/api/docs](http://localhost:9000/api/docs/) para obter a documentação no formato JSON.

Também optei por deixar registrado as requisições no formato json para ser importados via Postman. Esse arquivo pode ser encontrado na pasta [/docs/e-Core hiring exercise.postman_collection.json](./docs/e-Core%20hiring%20exercise.postman_collection.json).

### Diagrama ER

Para documentar o modelo de dados proposto, criei um sql que julgo representar as tabelas utilizadas nos micro-serviços `Teams` e `Users`, bem como o modelo de entidades que utilizei para montar o micro-serivço `Roles`.

Esse arquivo SQL pode ser encontrado na pasta [/docs/db.sql](./docs/db.sql).

Utilizando este arquivo SQL, seguindo o mesmo padrão de diagrama como código, gerei um diagrama modelo de entidades-relacionameto, que pode ser encontrado na pasta [/docs/dbdocs.io](./docs/dbdocs.io). Para visualizar o diagrama gerado, basta copiar o código deste arquivo e colar no site https://dbdiagram.io/d.

## Instalação

### Pre-requisitos

## Uso

## Contribuição

## Possíveis melhorias

1. Achei muito simplórios dados retornados dos endpoint `teams` e `users`.
 <!-- 1. No endpoint users o que eu faria
    1. O `displayName` parece ser um username que o usuário usa pra fazer login. Trocaria o nome dessa propriedade para `username`;
    2. Incluiria o atributo `name` que seria o nome completo real do usuário, ex "Fulano de Tal";
    3. Caso fosse necessário exibir um nome curto, ex "Fulano", incluiria um atributo `shortName` ou `firstName`;
2. No endpoint teams o que eu faria
    1. Dentro de cada time Incluiria uma lista -->
3. Ambos os endpoint estão retornando todos os dados sem paginação. Incluiria a paginação de forma opcional nos endpoint
4. A busca do time pelo Id está retornando nulo com HTTP:200. Alteraria para retornar 204:NO_CONTENT
