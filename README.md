# Clínica API - Gestão de Pacientes, Médicos e Consultas

API RESTful desenvolvida em Spring Boot para gerenciar as operações de uma clínica, incluindo o cadastro de pacientes, médicos e o agendamento de consultas. O projeto foi desenvolvido como parte da disciplina de SOA do Prof. Salatiel Luz Marinho.

**Aluno:** Guilherme Daher  
**RM:** 98611  
**Turma:** 3ESPW

## Tecnologias Utilizadas
- Java 21 & Spring Boot 3.3
- Spring Data JPA & Hibernate
- Oracle Database
- Flyway (para versionamento do banco)
- SpringDoc OpenAPI (Swagger)

## Como Executar

1. **Banco de Dados**: O projeto está configurado para usar o banco de dados Oracle da FIAP. As tabelas serão criadas automaticamente pelo Flyway na primeira vez que a aplicação for executada.

2. **Executar a Aplicação**: Use o comando Maven para iniciar o servidor.
    ```bash
    mvn spring-boot:run
    ```

3. **Acessar a API**: A aplicação estará disponível em `http://localhost:8080`.

## Documentação e Testes com Swagger

Para visualizar e interagir com todos os endpoints da API, acesse a interface do Swagger UI no seu navegador após iniciar a aplicação:

- **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

No Swagger, você pode testar cada funcionalidade, como criar um médico, agendar uma consulta e ver as respostas em tempo real.

## Endpoints da API

### Pacientes
- `POST /pacientes`: Cria um novo paciente.
- `GET /pacientes`: Lista todos os pacientes de forma paginada.

### Médicos
- `POST /medicos`: Cria um novo médico.
- `GET /medicos`: Lista todos os médicos de forma paginada.
- `GET /medicos/{id}`: Busca um médico pelo ID.
- `PUT /medicos/{id}`: Atualiza os dados de um médico.
- `DELETE /medicos/{id}`: Remove um médico.

### Consultas
- `POST /consultas`: Agenda uma nova consulta.
- `DELETE /consultas/{id}`: Cancela o agendamento de uma consulta.

## Sobre as 03 melhorias solicitadas
1. Sugestões de Evolução:
- Validação de Agendamento: Impedir que um médico tenha mais de uma consulta no mesmo horário.
- Autenticação e Autorização (JWT): Adicionar segurança para diferenciar perfis (paciente, médico, admin).
- Gestão de Horários do Médico: Permitir que médicos configurem seus horários de atendimento disponíveis.

2. Melhoria Implementada: A primeira sugestão (Validação de Agendamento) foi implementada. Para isso, adicionei um novo método ao `src/main/java/com/example/clinica/repository/ConsultaRepository.java`.
