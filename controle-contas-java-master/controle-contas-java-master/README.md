#########################################
############## PORTUGUÊS ###############
#########################################


- Aplicação desenvolvida para o Gerenciamento de Contas

- A aplicação foi desenvolvida utilizando: Spring Boot, Rest Services, JPA/Hibernate, Thymeleaf, Maven e h2database;
- Ao inicializar a aplicação, serão inseridas Contas e Clientes fictícios automaticamente;
- E os testes da aplicação foram feitos com: Selenium;

- Se você quiser fazer um build completo, execute o comando 	   mvn clean install
- Se você quiser somente executar os testes, execute o comando     mvn test

- Para execução da mesma, deve-se seguir um dos dois caminhos: 
* Carregar o projeto em uma IDE(Como o Eclipse) e executar a sua classe Principal (HubfintechApplication) como uma Java Application.  

* Ir no caminho que encontra-se o pom.xml e executar: 			   mvn spring-boot:run
* Abra o browser e acesse a aplicação pelo endereço 		       http://localhost:8080

- O sistema é composto por 4 telas principais:
* 1 CRUD de Clientes;
* 1 CRUD de Contas;
* 1 Tela para realizar Transferências ou Aportes;
* 1 Tela de consulta de históricos de Transferências, onde também podem ser realizados os Estornos;

