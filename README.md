# e-Core hiring exercise

## Descrição

Todo o código desta API foi armazendo no meu repositório do Github https://github.com/raphaelrdsoares/e-core-hiring-exercise, que está temporáriamente com visubilidade pública para a conclusão desse desafio.

## Funcionalidades

Colocar um h2 com detalhes da implementação: Colocar o modelo de arquitetura seguido, Colocar que o banco de dados foi feito na forma de arquivos.

Optei por documentar o uso de cada uma das funcionalidades criadas através do BDD com sintaxe Gherkin. Dessa forma, detalho o uso de cada endpoint e seus respectivos cenários, validando a entrega da funcionalidade.

### Criar nova role

<details>
    <summary>Mais detalhes</summary>

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

Cenário A1: Criação de uma role não default com sucesso\
DADO QUE a aplicação está iniciada\
E não cadastrei nenhuma role previamente\
QUANDO envio uma requisição passando um código e um nome qualquer\
E informo que não é uma role default\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário A2: Criação de uma role não default com sucesso\
DADO QUE a aplicação está iniciada\
E não cadastrei nenhuma role previamente\
QUANDO envio uma requisição passando um código e um nome qualquer\
E não informo o campo 'default' no payload\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados\
E no payload vejo que a role foi cadastrada como não default

Cenário B: Criação de uma role default com sucesso\
DADO QUE a aplicação está iniciada\
E não cadastrei nenhuma role previamente\
QUANDO envio uma requisição passando um código e um nome qualquer\
E informo que não é uma role default\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário C: Substituir uma role default com sucesso\
DADO QUE a aplicação está iniciada\
E já cadastrei uma role default previamente\
QUANDO envio uma requisição passando um código e um nome qualquer\
E informo que é uma role default\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário D: Erro ao criar uma role sem informar os campos obrigatórios\
DADO QUE a aplicação está iniciada\
E não cadastrei nenhuma role previamente\
QUANDO envio uma requisição para criar um role\
E não informo o código ou o nome da role\
ENTÃO recebo o retorno HTTP BAD_REQUEST:400\
E no payload de resposta uma mensagem informando que não é possível cadastrar uma role sem informar os campos obrigatórios

Cenário E: Erro ao criar uma role que já existe\
DADO QUE a aplicação está iniciada\
E não cadastrei nenhuma role previamente\
QUANDO envio uma requisição para criar um role\
E informo como código uma das roles pre-definidas (ex: dev)\
ENTÃO recebo o retorno HTTP CONFLICT:409\
E no payload de resposta uma mensagem informando que já existe uma role com esse código

</details>

### Criar memberships

<details>
    <summary>Mais detalhes</summary>
**Contexto**

Outro requisito foi a criação do endpoint de cadastro de memberships. Esse endpoint realiza a atribuição de uma role para um usuário de um time. Na requisição deve ser informado o código da role, o id do time e o id do usuário.

É possível não informar a role na requisição, apenas se houver uma role default já cadastrada. Caso não exista uma role default, será retornado um erro.

Esse endpoint possui uma integração com o micro-serviço `Teams` para buscar as informações do time e os usuários que fazem parte deste time.

Um usuário só pode ter uma role dentro de um time. Caso seja efetuada uma requisição informando uma outra role para um usuário de um time, essa role sobrescreverá a role atual do usuário no time.

A atribuição é feita de uma por uma. Não é permitida, através de uma única requisição, a atribuição de uma role para um usuário em todos os times que ele faz parte ou a atribuição de uma role para todos os membro de um time.

Durante o processamento da requisição, é verificado se a role já está cadastrada e também se o time existe e se o usuário existe dentro do time.

**Notas técnicas**

Endpoint para atribuição de roles:

```JSON
// POST /api/roles/memberships

{
  "roleCode": "dev", // código da role. Campo opcional. Valor default: role default cadastrada no banco
  "teamId": "b59c9365-15e3-5498-bc2e-35a3f3fed9e1", // Id do time.  Campo obrigatório
  "userId": "4961349e-38dd-560c-818f-c7d021149441" //  Id do usuário.  Campo obrigatório
}

```

**Cenários**

Cenário A: Campos obrigatório não informados\
DADO QUE a aplicação está iniciada\
QUANDO realizado uma requisição para atribuir uma role\
E não informo o id do time ou id do usuário\
ENTÃO recebo o retorno HTTP BAD_REQUEST:400\
E no payload da resposta um mensagem informando que não é possível atribuir uma role sem informar os campos obrigatórios

Cenário B: Role não existe\
DADO QUE a aplicação está iniciada\
QUANDO realizado uma requisição para atribuir uma role\
E informo o código de uma role que não está cadastrada\
ENTÃO recebo o retorno HTTP NOT_FOUND:404\
E no payload da resposta um mensagem informando que a role não foi encontrada

Cenário C: Time não existe\
DADO QUE a aplicação está iniciada\
E possuo o código de uma role existente\
QUANDO realizado uma requisição para atribuir uma role\
E informo id de um time que não existe\
ENTÃO recebo o retorno HTTP NOT_FOUND:404\
E no payload da resposta um mensagem informando que time não foi encontrado

Cenário D: Usuário não existe dentro do time\
DADO QUE a aplicação está iniciada\
E possuo o código de uma role existente\
E possuo o id de um time existente\
QUANDO realizado uma requisição para atribuir uma role\
E informo id de um usuário que não pertence ao time\
ENTÃO recebo o retorno HTTP NOT_FOUND:404\
E no payload da resposta um mensagem informando que o usuário não pertence ao time

Cenário E: Erro ao atribuir sem informar a role e role default não existe\
DADO QUE a aplicação está iniciada\
E não existe uma role default cadastrada\
E possuo o id de um time existente\
E possuo o id de um usuário que pertence ao time\
QUANDO realizado uma requisição para atribuir uma role\
E não informo uma role\
ENTÃO recebo o retorno HTTP NOT_FOUND:404\
E no payload da resposta um mensagem informando que não existe uma role default

Cenário F1: Atribuição com sucesso para um membro do time\
DADO QUE a aplicação está iniciada\
E possuo o código de uma role existente\
E possuo o id de um time existente\
E possuo o id de um usuário que pertence ao time\
QUANDO realizado uma requisição para atribuir uma role informando todos os dados\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário F2: Atribuição com sucesso para o líder do time\
DADO QUE a aplicação está iniciada\
E possuo o código de uma role existente\
E possuo o id de um time existente\
E possuo o id de um usuário que é o líder do time\
QUANDO realizado uma requisição para atribuir uma role informando todos os dados\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário G: Atribuição com sucesso sem informar a role\
DADO QUE a aplicação está iniciada\
E existe uma role default cadastrada\
E possuo o id de um time existente\
E possuo o id de um usuário que é o líder do time\
QUANDO realizado uma requisição para atribuir uma role informando todos os dados\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

Cenário H: Sobrescrita de atribuição com sucesso\
DADO QUE a aplicação está iniciada\
E já existe uma membership para o usuário no time\
E possuo o código de uma role diferente da existente na membership\
E possuo o id de um time existente\
E possuo o id de um usuário que é o líder do time\
QUANDO realizado uma requisição para atribuir uma role informando todos os dados\
ENTÃO recebo o retorno HTTP CREATED:201\
E no payload de resposta os dados cadastrados

</details>

### Consultar memberships

<details>
    <summary>Mais detalhes</summary>

**Contexto**

A última solicitação foi que houvesse uma forma de consultar as memberships criadas. Foi pedido que, dada um role, fosse retornado todos os memberships associados, e que, dada uma membership (team + user), fosse retornada a role. Para isso eu criei apenas 1 endpoint, que aceita 3 parâmetros, roleCode, teamId e userId, e retorna todas as memberships relacionadas a esses parâmetros.

A única exigência é que pelo menos 1 dos parâmetros seja preenchido, caso contrário, é retornado um erro.

**Notas técnicas**

Endpoint para consulta de memberships:

```JSON
// GET /api/roles/memberships?roleCode=dev&teamId=b59c9365-15e3-5498-bc2e-35a3f3fed9e1&userId=4961349e-38dd-560c-818f-c7d021149441

// payload da resposta
[
    {
      "roleCode": "dev",
      "teamId": "b59c9365-15e3-5498-bc2e-35a3f3fed9e1",
      "userId": "4961349e-38dd-560c-818f-c7d021149441"
    }
]

```

**Cenários**

Cenário A: Erro na consulta sem informar nenhum parâmetros\
DADO QUE a aplicação está iniciada\
QUANDO realizado uma requisição sem informar nenhum parâmetro\
ENTÃO recebo o retorno HTTP BAD_REQUEST:400\
E no payload de resposta uma mensagem informando que é necessário passar pelo menos 1 parâmetro

Cenário B: Não há memberships cadastradas para o conjunto de parâmetros\
DADO QUE a aplicação está iniciada\
E não existe nenhuma membership cadastrada\
QUANDO realizado uma requisição com algum parâmetro válido\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista vazia

Cenário C: Consulta todos os membership de uma role\
DADO QUE a aplicação está iniciada\
E existe várias membership cadastradas para uma role\
QUANDO realizado uma requisição o código da role\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista de todas as memberships dessa role

Cenário D: Consulta todos os membership de um team\
DADO QUE a aplicação está iniciada\
E existe várias membership cadastradas para um team\
QUANDO realizado uma requisição com o id deste team\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista de todas as memberships desse team

Cenário E: Consulta todos os membership de um user\
DADO QUE a aplicação está iniciada\
E existe várias membership cadastradas para um user\
QUANDO realizado uma requisição com o id deste user\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista de todas as memberships desse user

Cenário F: Consulta todos os membership de um team com uma role\
DADO QUE a aplicação está iniciada\
E existe várias membership cadastradas para um team e uma role\
QUANDO realizado uma requisição com o id deste team e o código desta role\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista de todas as memberships desse team com essa role

Cenário G: Consulta todos os membership de um user com uma role\
DADO QUE a aplicação está iniciada\
E existe várias membership cadastradas com uma role para um user\
QUANDO realizado uma requisição com o id deste user e o código desta role\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista de todas as memberships desse user com essa role

Cenário H: Consulta o membership de um user em um team\
DADO QUE a aplicação está iniciada\
E existe uma membership cadastrada para um user em um team\
QUANDO realizado uma requisição com o id deste user e o id deste team\
ENTÃO recebo o retorno HTTP OK:200\
E no payload de resposta uma lista com a membership desse user neste team

</details>

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

## Detalhes da Implementação

Para a implementação eu optei por seguir o modelo de Arquitetura Limpa, isolando as regras de negócio dos detalhes de infraestrutura e de frameworks, na camada de UseCases e Domain.

Na camada de repositórios, como o desafio é relativamente curto, eu optei por não utilizar um banco de dados, e sim um sistema de arquivos, gerados e carregados assim que a aplicação inicia. Todas as entidades do tipo `Roles` são salvas no arquivo `database/roles.json` e as entidades tipo `Membership` são salvas no arquivo `database/memberships.json`. Toda nova persitência realizada pelas classes `Repository` são persistidos também nos arquivos `.json`.

Sobre os testes, eu utilizei uma abordagem mista de testes unitários e de integração. Realizei os testes unitários apenas na camada de UseCases, que é a camada mais crítica da aplicação, onde estão concentradas as regras de negócio. Todas as outras camadas são cobertas pelos testes de integração.

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
