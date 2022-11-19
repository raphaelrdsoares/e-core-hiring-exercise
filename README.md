# e-core-hiring-exercise

## Possíveis melhorias

1. Achei muito simplórios dados retornados dos endpoint `teams` e `users`.
    <!-- 1. No endpoint users o que eu faria
       1. O `displayName` parece ser um username que o usuário usa pra fazer login. Trocaria o nome dessa propriedade para `username`;
       2. Incluiria o atributo `name` que seria o nome completo real do usuário, ex "Fulano de Tal";
       3. Caso fosse necessário exibir um nome curto, ex "Fulano", incluiria um atributo `shortName` ou `firstName`;
    2. No endpoint teams o que eu faria
       1. Dentro de cada time Incluiria uma lista  -->
2. Ambos os endpoint estão retornando todos os dados sem paginação. Incluiria a paginação de forma opcional nos endpoint
3. A busca do time pelo Id está retornando nulo
