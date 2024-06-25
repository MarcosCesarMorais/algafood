# Sistema de Delivery de Comida

Este projeto é uma aplicação de delivery de comida desenvolvida em Java com o framework Spring.

## Funcionalidades Principais

- **Cadastro de Restaurantes**: Os restaurantes podem se cadastrar na plataforma, informando seu nome, localização e tipo de cozinha.
  
- **Cadastro de Produtos**: Cada restaurante pode cadastrar os produtos que oferece, incluindo nome, descrição, preço e categoria.
  
- **Pedidos**: Os usuários podem visualizar os restaurantes disponíveis, selecionar produtos e realizar pedidos.
  
- **Autenticação e Autorização**: Autenticação de usuários (clientes e restaurantes) e autorização para acesso às funcionalidades específicas.

## Tecnologias Utilizadas

- **Java 11**: Linguagem de programação utilizada para desenvolver a aplicação.
  
- **Spring Boot**: Framework utilizado para desenvolvimento rápido e fácil de aplicações Spring.
  
- **Spring MVC**: Utilizado para construção da camada de controle da aplicação.
  
- **Spring Data JPA**: Facilita o acesso e a manipulação de dados relacionais no banco de dados.
  
- **Spring Security**: Utilizado para autenticação, autorização e segurança da aplicação.
  
- **H2 Database**: Banco de dados em memória utilizado para desenvolvimento e testes.

## Configuração do Ambiente de Desenvolvimento

1. **Pré-requisitos**:
   - JDK 11: Instale o Java Development Kit (JDK) versão 11.
   - Maven: Ferramenta de automação de compilação e gerenciamento de dependências.

2. **Clonar o Repositório**:
   ```bash
   git clone https://github.com/seu-usuario/sistema-delivery.git
   cd sistema-delivery
