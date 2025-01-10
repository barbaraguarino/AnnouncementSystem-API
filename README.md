# AnnouncementSystem-API

Este é o repositório da API para o [sistema de anúncios](https://github.com/Gabs543/AnnouncementSystem-FrontEnd), onde usuários podem criar, visualizar e buscar anúncios com diferentes filtros, além de interagir com outros usuários mediante um sistema de mensagens.

## Como Instalar e Rodar o Projeto

Este guia descreve os passos necessários para configurar, instalar e rodar o projeto localmente.
### Pré-requisitos

Certifique-se de que os seguintes softwares estão instalados na sua máquina:

1. Java Development Kit (JDK) - Versão 17 ou superior.
    > [Download JDK](https://www.oracle.com/java/technologies/downloads/)
2. Banco de Dados - PostgreSQL (ou outro configurado no projeto).
    > [Download PostgreSQL](https://www.postgresql.org/download/)
3. IDE - Visual Studio Code ou IntelliJ IDEA.
    > [Download VS Code](https://code.visualstudio.com/)

    > [Download Intellij](https://www.jetbrains.com/pt-br/idea/)
4. Maven - Para gerenciar as dependências (Caso não utilize uma IDE).
    > [Download Maven](https://maven.apache.org/download.cgi)

## Passos para Configuração

### 1. Clone o repositório

```bash
    git clone https://github.com/seu-usuario/nome-do-repositorio.git
    cd nome-do-repositorio
```

### 2. Configurar as variáveis de ambiente

Configure o arquivo `application.properties`, localizado na pasta `src/main/resources`,  modifique apenas as linhas relacionadas aos seguintes itens:
````properties
    # Configuração do Banco de Dados
    spring.datasource.url=jdbc:postgresql://localhost:5432/announcement-api
    spring.datasource.username=seu_usuário
    spring.datasource.password=sua_senha
````

### 3. Configure o Banco de Dados

1. Crie o banco de dados no PostgresSQL
    ````sql
       CREATE DATABASE announcement-api;
   ````
2. Verifique as credenciais do banco de dados estão corretas no arquivo `application.properties`.

## Passos para Compilação e Execução

### Intellij IDEA

1. Abrir o Projeto
   1. Abra o Intellij IDEA
   2. Clique em **File > Open** e selecione a pasta raiz do projeto.

2. Configurar o SDK
   1. Certifique-se de que o JDK está configurado:
        - Vá em **File > Project Structure > Project Settings > Project** e escolha a versão do JDK (Java 17 ou superior).

3. Rodar a Aplicação
    1. Navegue até a classe principal (`@SpringBootApplication`)
       - Está localizado no arquivo `AnnouncementSystemApiApplication.java` na pasta `src/main/java/com/system/announcement`.
    2. Clique com o botão direito na classe e selecione Run 'Application' (ou o nome equivalente da classe).

### Visual Studio Code (VS Code)

1. Abrir o Projeto
   1. Abra o VS Code.
   2. Selecione a pasta do projeto com **File > Open Folder** e selecione a pasta raiz do projeto.

2. Configure o Ambiente
   1. Certifique-se de ter o Extension Pack for Java instalado.
       - Instale na seção Extensions do VS Code.
   2. Verifique se o JDK está configurado corretamente.

3. Rodar a Aplicação
   1. Navegue até a classe principal (`@SpringBootApplication`)
       - Está localizado no arquivo `AnnouncementSystemApiApplication.java` na pasta `src/main/java/com/system/announcement`.
   2. Clique no botão **Run/Debug** que aparece acima do método `main` da classe.

### Terminal / Prompt de Comando (CMD)

1. Navegar até o Projeto
   1. Abra o terminal ou CMD.
   2. Navegue até a pasta raiz do projeto:
        ````bash
        cd caminho/para/a/pasta/AnnouncementSystem-API
        ````
2. Compilar o Projeto
    1. Compile o projeto e baixe as dependências com o Maven
        ```bash
       mvn clean install
       ```
3. Rodar a Aplicação
   1. Use o Maven para executar o projeto diretamente
        ```bash
       mvn spring-boot:run
       ```
        Ou execute o arquivo JAR gerado (normalmente na pasta `target/`)
      ```bash
      cd target
      java -jar nome-do-projeto.jar
       ```

## Funcionalidades

### Funcionalidades já implementadas:

1. **Criação de Anúncios**
    - Usuários autenticados podem criar anúncios, especificando informações como título, conteúdo, cidade, categorias, preço e imagens.
    - A criação de anúncios utiliza DTOs para garantir que os dados sejam validados e corretamente processados.

2. **Exibição de Anúncios**
    - Anúncios podem ser visualizados em formato de lista com suporte à paginação.
    - Filtros de pesquisa são aplicados para permitir a busca por categorias, cidades e preços.
    - A exibição é feita com a ordenação dos anúncios pela data em ordem decrescente.

3. **Detalhes do Anúncio**
    - Cada anúncio tem uma página detalhada com todas as informações sobre o mesmo, incluindo as opções de contato com o anunciante.

4. **Autenticação**
    - O sistema de autenticação utiliza Spring Security para proteger as rotas da API, permitindo que apenas usuários autenticados acessem todas as funcionalidades.
    - Utilização de JWT (JSON Web Token) para autenticação e autorização.

5. **Favoritar, Remover do Favorito e Listar Favoritos**
   - O sistema permite que usuários possam favoritar anúncios ou remover dos favoritos, dessa forma é criado uma lista de anúncios favoritos. 

6. **Editar Anúncio**
    - O sistema permite que o autor do anúncio possa editar o anúncio já criado.

7. **Chat**
    - O sistema permite que o usuário abra chats para conversar sobre algum anúncio com o autor do mesmo.

8. **Avaliação**
   - O sistema permite que os usuários participante des chats possam avaliar uns aos outros apos o encerramento do chat.

## Tecnologias Utilizadas

## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para desenvolvimento rápido de aplicações, simplificando a criação de APIs RESTfull.
- **PostgreSQL**: Banco de dados relacional de alto desempenho para armazenamento dos dados da aplicação.
- **JWT**: Padrão de autenticação baseado em tokens JSON para garantir a segurança das requisições.
- **Spring Security**: Framework para implementação de segurança, autenticação e autorização de usuários.
- **Maven**: Ferramenta de gerenciamento de dependências e automação de builds para projetos Java.
- **Lombok**: Biblioteca para redução de código boilerplate, gerando automaticamente getters, setters, construtores e outros.
- **Validation**: API para validação de dados de entrada, garantindo que os dados enviados para o back-end estejam corretos e completos.
- **Spring WebSocket**: Biblioteca para comunicação em tempo real baseada no protocolo WebSocket, ideal para aplicações que demandam atualizações instantâneas, como chats e notificações.

## **Entidades**

### 1. **Announcement (Anúncio)**
Representa um anúncio publicado no sistema.

**Atributos:**
- `id` (UUID) — Identificador único do anúncio.
- `title` (String) — Título do anúncio.
- `content` (String) — Conteúdo do anúncio.
- `author` (User) — Autor do anúncio (relacionamento ManyToOne com a entidade `User`).
- `city` (City) — Cidade onde o item está localizado (relacionamento ManyToOne com a entidade `City`).
- `date` (Timestamp) — Data do anúncio.
- `categories` (Set<Category>) — Conjunto de categorias associadas ao anúncio (relacionamento ManyToMany com a entidade `Category`).
- `price` (Float) — Preço do item anunciado.
- `status` (AnnouncementStatus) — Status do anúncio.
- `favorites` (Set<Favorite>) - Conjunto de usuários que favoritou o anúncio.
- `chats` (Set<Chat>) - Conjunto de chats vinculados ao anúncio.
- `deletionDate` (Timestamp) — Data de exclusão do anúncio, se aplicável.
- `imageArchive` (String) — Pasta onde as imagens do anúncio estão armazenadas no Firebase.


### 2. **City**
Representa as cidades disponíveis para filtro nos anúncios.

**Atributos:**
- `id` (UUID) — Identificador único da cidade.
- `name` (String) — Nome da cidade (único).
- `announcements` (Set<Announcement>) — Conjunto de anúncios associados a essa cidade (relacionamento OneToMany com a entidade `Announcement`).


### 3. **Category**
Representa as categorias de anúncios (ex.: eletrônicos, móveis, carros).

**Atributos:**
- `id` (UUID) — Identificador único da categoria.
- `name` (String) — Nome da categoria (único).
- `announcements` (Set<Announcement>) — Conjunto de anúncios associados a essa categoria (relacionamento ManyToMany com a entidade `Announcement`).


### 4. **User**
Representa um usuário do sistema.

**Atributos:**
- `email` (String) — Identificador único do usuário (email).
- `password` (String) — Senha criptografada do usuário.
- `name` (String) — Nome do usuário.
- `icon` (String) — Ícone ou imagem do perfil do usuário (opcional).
- `type` (UserType) — Tipo do usuário (ex.: ANUNCIA, INTERESSADO).
- `score` (Float) — Pontuação do usuário.
- `role` (UserRole) — Papel do usuário (ex.: ADMIN, USER).
- `blocked` (Boolean) — Indica se o usuário está bloqueado.
- `deleted` (Boolean) — Indica se o usuário foi excluído.
- `deletedDate` (Timestamp) — Data de exclusão do usuário, se aplicável.
- `announcements` (Set<Announcement>) — Conjunto de anúncios criados pelo usuário (relacionamento OneToMany com a entidade `Announcement`).
- `grade` (float) - Total de todas as notas das avaliações
- `numAssessment` (int) - Número total das avaliações
- `favorites` (Set<Favorite>) - Conjunto de anúncios que anúncios favoritados (relacionamento OneToMany com a entidade `Favorite`).
- `chatsUser` (Set<Chat>) - Conjunto que chats que o usuário abriu sobre algum anúncio (relacionamento OneToMany com a entidade `Chat`).
- `chatsAdvertiser` (Set<Chat>) - Conjunto de chats que o usuário possui sobre algum anúncio que criou (relacionamento OneToMany com a entidade `Chat`).
- `messages` (Set<Message>) - Conjunto de mensagem que o usuário enviou (relacionamento OneToMany com a entidade `Message`).
- `myReviwes` (Set<Assessment>) - Conjunto de avaliações que o usuário realizou (relacionamento OneToMany com a entidade `Assessment`).
- `assessments` (Set<Assessment>) - Conjunto de avaliações que o usuário recebeu (relacionamento OneToMany com a entidade `Assessment`).

### 4. **Favorite**
Representa os anúncios favoritos de cada um dos usuários do sistema.

**Atributos:**
- `announcement` (Announcement) — Identifica o anúncio (relacionamento ManyToOne com a entidade `Announcement`).
- `user` (User) — Identifica o usuário (relacionamento ManyToOne com a entidade `User`).

### 5. **Chat**
Representa os chats criados por cada um dos usuários do sistema.

**Atributos:**
- `id` (UUID) - Identificador único do chat
- `status` (ChatStatus) - Status do chat
- `dateOpen` (Timestamp) - Data de abertura do chat
- `dateDeleted` (Timestamp) - Data de deleção do chat
- `dateClose` (Timestamp) - Data de fechamento/encerramento do chat
- `dateLastMessage` (Timestamp) - Data da última mensagem enviada
- `isEvaluatedByAdvertiser` (Boolean) - Indica se o anunciante já avaliou o usuário após o fechamento do chat
- `isEvaluatedByUser` (Boolean) - Indica se o usuário já avaliou o anunciante após o fechamento do chat.
- `user` (User) - Usuário que abriu o chat (relacionamento ManyToOne com a entidade `User`).
- `advertiser` (User) - Autor do anúncio (relacionamento ManyToOne com a entidade `User`).
- `announcement` (Announcement) - Anúncio que foi vinculado ao chat (relacionamento ManyToOne com a entidade `Announcement`).
- `messages` (Set<Message>) - Lista de mensagem trocadas no chat (relacionamento OneToMany com a entidade `Message`).
- `assessments` (Set<Assessment>) - Lista de avaliações que os participantes do chat fizeram um do outros (relacionamento OneToMany com a entidade `Assessment`).

### 6. **Message**
Representa as mensagens enviadas entre os usuários por meio dos chats.

**Atributos:**
- `id` (UUID) - Identificador único do chat
- `content` (String) - Conteúdo da mensagem
- `date` (Timestamp) - Data do envio da mensagem
- `chat` (Chat) - Chat que a mensagem faz parte (relacionamento ManyToOne com a entidade `Chat`).
- `sender` (User) - Usuário que enviou a mensagem (relacionamento ManyToOne com a entidade `User`).

### 7. **Assessment**
Representa as avaliações feitas pelos usuários após conversa no chat.

**Atributos:**
- `id` (UUID) - Identificador único do chat
- `title` (String) - Título da avaliação
- `description` (String) - Conteúdo da avaliação
- `grade` (float) - Nota da avaliação
- `date` (Timestamp) - Data da avaliação
- `evaloutorUser` (User) - Usuário que fez a avaliação (relacionamento ManyToOne com a entidade `User`).
- `ratedUser` (User) - Usuário avaliado (relacionamento ManyToOne com a entidade `User`).
- `chat` (Chat) - Chat que foi motivo da avaliação (relacionamento ManyToOne com a entidade `Chat`).

## Endpoints da API

### 1. **Anúncios**

- **POST /announcement/create**  
  Cria um novo anúncio.

- **POST /announcement/filter**  
  Retorna uma lista de anúncios com paginação e filtros (por título, descrição, cidade, categoria, preço e tipo de usuário).

- **GET /announcement/{id}**  
  Retorna os detalhes de um anúncio específico.

- **POST /announcement/edit/{id}**  
  Edita o anúncio passado pelo id na url.

- **GET /announcement/closed**  
  Retorna uma lista de todos os anúncios fechados que o usuário requeredor possui.

- **GET /announcement/suspended**  
  Retorna uma lista de todos os anúncios suspensos que o usuário requeredor possui.

- **GET /announcement/open**  
  Retorna uma lista de todos os anúncios abertos que o usuário requeredor possui.

- **DELETE /announcement/{id}**  
  Muda o status para deletado o anúncio passado pelo id na url

- **GET /announcement/user/{email}**  
  Retorna uma lista de todos os anúncios abertos que o usuário dono do email passado pelo url possui.

### 2. **Autenticação**

- **POST /auth/login**  
  Realiza o login do usuário e retorna um JWT.

### 3. **Categorias**

- **GET /category**  
  Realiza uma lista com as categorias cadastradas no sistema.

### 4. **Cidades**

- **GET /city**  
  Realiza uma lista com as cidades cadastradas no sistema.

### 5. **Favoritos**

- **POST /favorite/{id}**  
  Adiciona o anúncio passado pelo id como favorito do usuário que fez a requisição.

- **GET /favorite/{id}**  
  Verifica se o anúncio passo por id é um favorito.

- **GET /favorite**  
  Retorna uma lista de anúncios favoritos do usuário. 

- **DELETE /favorite/{id}**  
  Remove o anúncio da lista de favoritos.  

### 6. **Avaliações**

- **POST /assessment**  
  Cria uma nova avaliação com os dados passado pelo JSON.

- **GET /assessment/reviews**  
  Retorna uma lista de avaliações que o usuário requeredor realizou.

- **GET /assessment/assessments/{email}**  
  Retorna uma lista de avaliações do usuário dono do email passado pela url.

### 7. **Chat**

- **POST /chat/{id}**  
  Cria um novo chat vinculado ao anúncio passado pelo id na url.

- **GET /chat**  
  Retorna uma lista de chats que o usuário requeredor possui.

- **POST /chat/close/{id}**  
  Fecha o chat passado por id.

### 8. **Mensagens**

- **MESSAGE MAPPING /send-message**  
  Cria a mensagem enviada e manda para todos os participantes do chat.

- **GET /chat/message/{id}**  
  Retorna uma lista de mensagens que o chat passado por id na url possuí.

### 3. **Usuário**

- **GET /user/{email}**  
  Retorna os dados do usuário passado pelo email na url.
