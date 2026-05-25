
# Zentra Flow

O **Zentra Flow** é uma plataforma SaaS de agendamento para clínicas, desenvolvida para otimizar o fluxo de atendimento de pacientes. O sistema permite o gerenciamento de clínicas, procedimentos e horários disponíveis, oferecendo uma experiência simples e eficiente para agendamentos. Além disso, conta com suporte opcional para solicitação de transporte/motorista, tornando o processo mais acessível e organizado tanto para pacientes quanto para equipes administrativas.

---

## 🛠️ Tecnologias Utilizadas

>
> ### Stack Principal (Linguagem e Framework)
> * **Java 25** (Versão da plataforma utilizada para o desenvolvimento).
> * **Spring Boot 3.x** (Framework base: Web, Data JPA, Validation, DevTools).

>
> ### Banco de Dados e Migrações
> * **PostgreSQL** (Banco de dados relacional principal rodando via container).
> * **Flyway** (Ferramenta para controle e versionamento automático de migrações SQL).


> ### Infraestrutura e Produtividade
> * **Docker & Docker Compose** (Ambiente de banco de dados isolado e reprodutível).
> * **Lombok** (Biblioteca para produtividade e eliminação de código boilerplate).


> ### Sistema de Logs
> * **SLF4J / Logback** (Mecanismo de logs assíncronos com suporte a Mapped Diagnostic Context - MDC).
---

## ⚙️ Funcionalidades
### 🔒 Security Audit Logger

#### A plataforma conta com um mecanismo centralizado e resiliente para o rastreamento de ações críticas (Ex: tentativas de login, alterações cadastrais, agendamentos):
* **Rastreio de Ponta a Ponta:** Integração via filtros HTTP com o MDC da camada de logs, amarrando um identificador exclusivo (traceId) a todo o ciclo de vida da requisição.
* **Resiliência a Falhas:** Isolamento com tratamento estruturado de exceções (try-catch), garantindo que instabilidades temporárias no banco de dados de logs não bloqueiem o agendamento de consultas ou serviços essenciais.

## 🗄️ Modelo de Dados - Estrutura da Tabela log_auditoria
#### A tabela de auditoria foi modelada integrando os campos de controle e auditoria automática herdados da classe abstrata global (BaseEntity):

| Coluna | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `UUID` (PK) | Identificador universal e único do registro de log. |
| `acao` | `VARCHAR(50)` | O tipo de operação realizada (ex: `USER_LOGIN_TEST`). |
| `descricao` | `TEXT` | Detalhes em formato textual legível da ação executada. |
| `trace_id` | `VARCHAR(10)` | ID de correlação único recuperado automaticamente do MDC. |
| `version` | `INTEGER` | Controle de concorrência otimista gerenciado pelo Hibernate. |
| `created_date` | `TIMESTAMP` | Data e hora exata da geração do registro no banco. |
| `created_by` | `VARCHAR(100)` | Usuário responsável pela ação ou marcador automático `SISTEMA`. |
| `last_modified_date` | `TIMESTAMP` | Data da última alteração física realizada no registro. |
| `last_modified_by` | `VARCHAR(100)` | Último ator/usuário a alterar os metadados do registro. |
| `deleted` | `BOOLEAN` | Flag ativa de controle para exclusão lógica (Soft Delete). |

##  Como Executar o Projeto Localmente
### Siga o passo a passo abaixo para configurar o ambiente e rodar a API na sua máquina.
#### 1. Clonar o repositório:
```Bash 

git clone [https://github.com/LuizGustavoSampaio/Zentra-Flow.git](https://github.com/LuizGustavoSampaio/Zentra-Flow.git)
cd Zentra-Flow
```
#### 2. Subir a infraestrutura de banco de dados (Docker):
<sub>**__Certifique-se de que o Docker está aberto e rodando no seu computador, e então execute:__**</sub>

```Bash 
docker compose up -d
```
#### 3. Iniciar a aplicação Spring Boot:
<sub>**__Você pode rodar diretamente pela sua IDE (como o IntelliJ IDEA) executando a classe `ZentraFlowApplication`, ou pelo terminal usando o Maven Wrapper:__**</sub>
```bash
Bash
./mvnw spring-boot:run
```
<sub>**__Nota: Assim que a aplicação iniciar, o Flyway fará a varredura automática da pasta db/migration e criará toda a estrutura de tabelas no PostgreSQL sem necessidade de scripts manuais.__**</sub>


## 🏗️ Estrutura do Projeto (Arquitetura)

O ecossistema do **Zentra Flow** segue rigidamente o padrão de arquitetura em camadas, distribuído da seguinte forma dentro do código-fonte:

```text
src/main/java/com/zentra/zentra_flow/
│
├── config/        
├── controllers/    
├── entities/       
├── filter/         
├── repositories/  
└── services/   
```

## 🏷️ Versionamento e Entregas (SemVer)
#### O projeto adota o padrão Semantic Versioning 2.0.0 (MAJOR.MINOR.PATCH) para gerenciar o ciclo de vida da plataforma e garantir estabilidade nas integrações.

### v0.1.0 (Project Fundation)
#### Esta primeira versão consolida a infraestrutura base e os padrões de arquitetura essenciais para o crescimento do sistema.
### O que já foi implementado até aqui:
+ Estruturação limpa em arquitetura de camadas (Controllers, Services, Repositories, Entities, etc.).
+ Configuração dos perfis de ambiente (application-dev.yml, prod.yml, test.yml).
+ Configuração do Docker Compose isolando o PostgreSQL para desenvolvimento.
+ Integração do Flyway (V1__create_table_audit_log.sql) para controle de versão do banco.
+ Criação da superclasse BaseEntity para injeção automática de dados de auditoria (Data de criação, Versão do Hibernate, Soft Delete).
+ Implementação do SecurityAuditLogger, o serviço autônomo de rastreabilidade de eventos e segurança.
