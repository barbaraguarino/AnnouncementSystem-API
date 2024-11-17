# AnnouncementSystem-API

Este é o repositório da API para o [sistema de anúncios](https://github.com/Gabs543/AnnouncementSystem-FrontEnd), onde usuários podem criar, visualizar e buscar anúncios com diferentes filtros, além de interagir com outros
usuários através de um sistema de mensagens.

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


## Tecnologias Utilizadas

## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para desenvolvimento rápido de aplicações, simplificando a criação de APIs RESTful.
- **PostgreSQL**: Banco de dados relacional de alto desempenho para armazenamento dos dados da aplicação.
- **JWT**: Padrão de autenticação baseado em tokens JSON para garantir a segurança das requisições.
- **Spring Security**: Framework para implementação de segurança, autenticação e autorização de usuários.
- **Maven**: Ferramenta de gerenciamento de dependências e automação de builds para projetos Java.
- **Lombok**: Biblioteca para redução de código boilerplate, gerando automaticamente getters, setters, construtores e outros.
- **Validation**: API para validação de dados de entrada, garantindo que os dados enviados para o back-end estejam corretos e completos.

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
- `deletionDate` (Timestamp) — Data de exclusão do anúncio, se aplicável.
- `imageArchive` (String) — Caminho onde as imagens do anúncio estão armazenadas no Firebase.


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
Representa um usuário do sistema (anunciante ou interessado).

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

## Endpoints da API

### 1. **Anúncios**

- **POST /announcement/create**  
  Cria um novo anúncio.

- **POST /announcement/filter**  
  Retorna uma lista de anúncios com paginação e filtros (por título, descrição, cidade, categoria, preço e tipo de usuário).

- **GET /announcement/{id}**  
  Retorna os detalhes de um anúncio específico.

- **GET /announcement/closed**  
  Retorna uma lista de todos os anúncios fechados que o usuário requisidor possui.

- **GET /announcement/suspended**  
  Retorna uma lista de todos os anúncios suspensos que o usuário requisidor possui.

- **GET /announcement/open**  
  Retorna uma lista de todos os anúncios abertos que o usuário requisidor possui.

### 3. **Autenticação**

- **POST /auth/login**  
  Realiza o login do usuário e retorna um JWT.

### 3. **Categorias**

- **GET /category**  
  Realiza uma lista com as categorias cadastradas no sistema.

### 3. **Cidades**

- **GET /city**  
  Realiza uma lista com as cidades cadastradas no sistema.