/*
 * Copy this code below and paste it in https://structurizr.com/dsl to see the C4 model diagram
*/

workspace "e-Core: Role Service"  {
    model {
        client = softwareSystem "Frontend or API" "Serivço externo que interage com micro-serviço de roles, como um frontend ou outra API " "Customer"
        rolesServiceSystem = softwaresystem "e-Core Roles Service" "Micro-serviço de backend, responsável por cadastrar e associar roles, e buscar memberships." {
            controllerContanier = container "Controllers" "Recebe as requisições HTTP REST" "" "Container"
            controllerDtoContainer = container "Controllers DTO's" "Mapeamento dos dados de entrada e saída das requisições" "" "Container"
            servicesContainer = container "Services" "Processa as requisições e faz injeção de dependência dos componentes necessários" "" "Container"
            useCasesContainer = container "UseCases" "Implementa as regras de negócio do sistema" "" "Container"
            domainContainer = container "Domain" "Contém os objetos de domínio da aplicação" "" "Container"
            repositoriesContainer = container "Repositories" "Integração com banco de dados para persistencia das entidades" "" "Container"
            entitiesContainer = container "Repository entities" "Mapeamento das entidades persistidas no banco" "" "Container"
            integrationsContainer = container "Integrations" "Integrações com API's externas" "" "Container"
            integrationDtoContainer = container "Integrations DTO's" "Mapeamento dos dados de entrada e saída das integrações com API's externas" "" "Container"
        }
        teamsServiceSystem = softwaresystem "e-Core Teams Service" "Micro-serviço de backend, responsável por buscar de todos os times e de time por id." "Existing System"
        databaseSystem = softwaresystem "Banco de dados" "Banco de dados usando um simples sistemas de arquivos." "Database"



        # relationships between software systems
        client -> rolesServiceSystem "Cria e associa roles, e busca as associações"
        rolesServiceSystem -> teamsServiceSystem "Busca todos os times ou busca por id"
        rolesServiceSystem -> databaseSystem "Salva todas as entidades em"
        
        // # relationships between software systems and containers
        client -> controllerContanier "Envia requisições HTTP REST (POST, GET, etc) para casdastrar roles e cadastrar e buscar memberships"
        integrationsContainer -> teamsServiceSystem "Envia requisições HTTP REST para realizar busca de times"
        repositoriesContainer -> databaseSystem "Persiste as entidades no"
        
        # relationships between containers
        controllerContanier -> controllerDtoContainer "Mapeia os dados de entrada e saída das requisições usando os"
        controllerContanier -> servicesContainer "Processa as requisições através do"
        servicesContainer -> controllerDtoContainer "Consome e retorna os dados das requisições usando os"
        servicesContainer -> useCasesContainer "Processa as regras de negócio dos serviços usando o"
        useCasesContainer -> controllerDtoContainer "Mapeia os dados de entrada e saída das regras de negócio usando o"
        useCasesContainer -> domainContainer "Converte os dados de entrada e saída para o"
        useCasesContainer -> repositoriesContainer "Persiste os dados usando o"
        useCasesContainer -> integrationsContainer "Consome os dados de API's externas usando o"
        useCasesContainer -> integrationDtoContainer "Mapeia os dados de entrada e saída das API's externas usando o"
        domainContainer -> controllerDtoContainer "Converte os objetos de domínio para os dados de entrada e saída"
        domainContainer -> integrationDtoContainer "Converte os objetos de domínio para os dados de entrada e saída"
        domainContainer -> entitiesContainer "Converte os objetos de domínio para as entidades de persitência"
        repositoriesContainer -> entitiesContainer "Mapeia os dados persistidos usando o"
        integrationsContainer -> integrationDtoContainer "Mapeia os dados de entrada e saída das requisições externas usando o"

    }
    views {
        styles {
            element "Person" {
                shape person
                background #08427b
                color #ffffff
            }
            element "Software System" {
                background #1168bd
                color #ffffff
            }
            element "Customer" {
                background #73a0d6
                color #ffffff
            }
            element "Existing System" {
                background #999999
                color #ffffff
            }
            element "External Database" {
                background #999999
                color #ffffff
                shape Cylinder
            }
            element "Container" {
                background #438dd5
                color #ffffff
            }
            element "Web Browser" {
                shape WebBrowser
            }
            element "Database" {
                shape Cylinder
            }
            element "Component" {
                background #85bbf0
                color #000000
            }

        }
  }
}