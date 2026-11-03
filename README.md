# SetupHub

Sistema web para cadastro, consulta e documentação de **filmes** e **setups** (PPG), com
painel de visualização, tags de filtro, imagens, documentação e feedbacks.

## Funcionalidades

- Cadastro e consulta de filmes e setups
- Filtro por tags e busca por nome
- Upload de imagens e anexo de documentação
- Registro de feedbacks
- Área administrativa com login (JWT)

## Tecnologias

- Java 17 (ajuste para a sua versão)
- Spring Boot, Spring Security (JWT), Spring Data JPA
- Maven
- Banco de dados: H2 (desenvolvimento) / PostgreSQL (produção)
- Front end: HTML, CSS e JavaScript (`src/main/resources/static`)

## Como rodar localmente

### Pré-requisitos
- JDK 17 ou superior
- Git

### Passos

```bash
git clone https://github.com/igor27oliver-tech/SetupHub.git
cd SetupHub
./mvnw spring-boot:run
```

No Windows (PowerShell):

```powershell
.\mvnw spring-boot:run
```

Depois, abra `http://localhost:8080`.

## Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `JWT_SECRET` | Chave secreta usada para assinar os tokens |
| `SPRING_DATASOURCE_URL` | URL do banco de dados |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco |
| `PORT` | Porta do servidor (padrão: 8080) |

## Estrutura do projeto

```
src/main/java
├── Controll/     # Controllers (rotas da API)
├── DTO/          # Objetos de requisição e resposta
├── Entity/       # Entidades do banco
├── Mapper/       # Conversão entre entidades e DTOs
├── Repository/   # Acesso ao banco
├── Security/     # Autenticação JWT
└── Service/      # Regras de negócio
src/main/resources
├── static/       # Front end
└── application.properties
```

## Deploy

O projeto pode ser publicado no [Railway](https://railway.com) a partir deste repositório,
configurando as variáveis de ambiente acima.

## Observações

- A pasta `uploads/` (imagens enviadas) não é versionada.
- Não commite senhas nem chaves secretas.

## Autor

Igor Oliveira — [@igor27oliver-tech](https://github.com/igor27oliver-tech)
