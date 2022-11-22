# e-Core hiring exercise

## Descrição

Todo o código desta API foi armazendo no meu repositório do Github https://github.com/raphaelrdsoares/e-core-hiring-exercise.

## Funcionalidades

Colocar um h2 com detalhes da implementação: Colocar o modelo de arquitetura seguido, Colocar que o banco de dados foi feito na forma de arquivos.

Optei por documentar o uso de cada uma das funcionalidades criadas através do BDD com sintaxe Gherkin. Dessa forma, detalho o uso de cada endpoint e seus respectivos cenários, validando a entrega da funcionalidade.

### Criar nova role

**Contexto**

Foi solicitado a criação de um endpoint para cadastro de Roles. Esse endpoint recebe um código e um nome da role e uma flag informando se é uma role default. O código da é uma chave única, ou seja, não pode haver 2 roles com o mesmo código.

O endoint não realiza atualização, apenas cadastro. Caso seja realizada uma requisição uma role com um código já existente, será retornado um erro.

Só pode existir uma única role default. Caso, durante o cadastro de uma nova role, seja informado que ela é uma role default, então a antiga role default é atualizada para constar como não-default.

**Notas técnicas**

Endpoint para cadastro de novas roles:

```JSON
// POST /api/roles

{
  "code": "ux", // código da role. Campo obrigatório
  "name": "User Experience", // nome da role.  Campo obrigatório
  "isDefault": false // informa se é role é ou não padrão.  Campo opcional. Valor default: false.
}

```

**Critérios de aceite**

Cenário A1: Criação de uma role não default com sucesso
DADO QUE a aplicação está iniciada
E não cadastrei nenhuma role previamente
QUANDO envio uma requisição passando um código e um nome qualquer
E informo que não é uma role default
ENTÃO recebo o retorno HTTP CREATED:201
E no payload de resposta os dados cadastrados

Cenário A2: Criação de uma role não default com sucesso
DADO QUE a aplicação está iniciada
E não cadastrei nenhuma role previamente
QUANDO envio uma requisição passando um código e um nome qualquer
E não informo o campo 'default' no payload
ENTÃO recebo o retorno HTTP CREATED:201
E no payload de resposta os dados cadastrados
E no payload vejo que a role foi cadastrada como não default

Cenário B: Criação de uma role default com sucesso
DADO QUE a aplicação está iniciada
E não cadastrei nenhuma role previamente
QUANDO envio uma requisição passando um código e um nome qualquer
E informo que não é uma role default
ENTÃO recebo o retorno HTTP CREATED:201
E no payload de resposta os dados cadastrados

Cenário C: Substituir uma role default com sucesso
DADO QUE a aplicação está iniciada
E já cadastrei uma role default previamente
QUANDO envio uma requisição passando um código e um nome qualquer
E informo que é uma role default
ENTÃO recebo o retorno HTTP CREATED:201
E no payload de resposta os dados cadastrados

Cenário D: Erro ao criar uma role sem informar os campos obrigatórios
DADO QUE a aplicação está iniciada
E não cadastrei nenhuma role previamente
QUANDO envio uma requisição para criar um role
E não informo o código ou o nome da role
ENTÃO recebo o retorno HTTP BAD_REQUEST:400
E no payload de resposta uma mensagem informando que não é possível cadastrar uma role sem informar os campos obrigatórios

Cenário E: Erro ao criar uma role que já existe
DADO QUE a aplicação está iniciada
E não cadastrei nenhuma role previamente
QUANDO envio uma requisição para criar um role
E informo como código uma das roles pre-definidas (ex: dev)
ENTÃO recebo o retorno HTTP CONFLICT:409
E no payload de resposta uma mensagem informando que já existe uma role com esse código

### Criar memberships

### Consultar memberships

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

Para documentar o modelo de dados proposto, criei um sql que julgo representar as tabelas utilizadas nos micro-serviços `Teams` e `Users`, bem como o modelo de entidades que utilizei para montar este micro-serivço `Roles`.

Esse arquivo SQL pode ser encontrado na pasta [/docs/db.sql](./docs/db.sql).

Utilizando este arquivo SQL, seguindo o mesmo padrão de diagrama como código, gerei um diagrama modelo de entidades-relacionameto, que pode ser encontrado na pasta [/docs/dbdocs.io](./docs/dbdocs.io). Para visualizar o diagrama gerado, basta copiar o código deste arquivo e colar no site https://dbdiagram.io/d.

## Instalação

### Pre-requisitos

## Uso

## Possíveis melhorias

1. Achei muito simplórios dados retornados dos endpoint `teams` e `users`.
 <!-- 1. No endpoint users o que eu faria
    1. O `displayName` parece ser um username que o usuário usa pra fazer login. Trocaria o nome dessa propriedade para `username`;
    2. Incluiria o atributo `name` que seria o nome completo real do usuário, ex "Fulano de Tal";
    3. Caso fosse necessário exibir um nome curto, ex "Fulano", incluiria um atributo `shortName` ou `firstName`;
2. No endpoint teams o que eu faria
    1. Dentro de cada time Incluiria uma lista -->
3. Ambos os endpoint estão retornando todos os dados sem paginação. Incluiria a paginação de forma opcional nos endpoint
 <!-- 4. A busca do time pelo Id está retornando nulo com HTTP:200. Alteraria para retornar 204:NO_CONTENT -->

4. Implementaria uma estratégia de logs, registrando logs tratar cada exceção, bem como ao receber novas requisições e ao registrar dados no banco.
