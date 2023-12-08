# Telemetria para robô de competição
## 💻 Sobre o projeto
O projeto engloba o uso de robôs seguidores de linha (Follow Line), com o objetivo de criar uma solução em Java, para que sejam contabilizadas todas as curvas realizadas pelo robô, durante a execução de um percurso. 
A contabilização das curvas auxiliará os operadores dos robôs a otimizar a performance, durante as competições.

## 🔧 Versão 
Esta é a versão beta da solução, avaliada positivamente por parte do cliente final, o Garagino.
A MangueByte propõe a evolução desta solução para uma solução com inteface gráfica, para demonstrar em tempo real a quantidade de curvas realizadas pelo robô e também o refinamento dos parâmetros para contabilização
das curvas, que será feito mediante testes com o robô rodando em um circuito juntamente com as informações dadas pela equipe.

😀 Como contribuir para o projeto
1. Como o sistema funciona e pra que funciona;
2. Clone na sua máquina o projeto e todas as dependências já estão configuradas;
3. O banco de dados é um banco em memória para testes (caso queira acessar o banco de dados em memória, o caminho é "localhost:8080(modifique para a porta que você está usando)/h2-console".
   Para acessar o login e senha estão no arquivo application-test.properties
4. O projeto roda em Spring Boot;
5. Suba a aplicação Spring;
6. No Postman, no endpoint '/dados/processar' será uma requisição do tipo POST, você seleciona um arquivo txt com as informações que serão salvas. No body(corpo da requisição),
   com a chave 'file' você seleciona o tipo para "file" e passa o arquivo como valor. Após retornar a mensagem de documento salvo com sucesso, 
   você altera o endpoint '/dados/listar' para o tipo GET, faz o envio novamente e retornará os valores tratados do arquivo passado no endopoint anterior. 
   Caso queira Deletar algum valor, basta alterar o endpoint '/dados/id(valor correspondente do id que deseja deletar) para uma requisição DELETE e realizar um novo envio.


## 📝 Licença
Este projeto é copyleft. :)

# projetomanguebyte
