# sicredi-project

Projeto para receber via linha de comando um arquivo csv e processar uma simulação de seu envio para o Banco Central e receber retorno do mesmo.

#Execução

Inicialmente para seu funcionamento em standalone é preciso fazer a geração do jar. Para isso é necessário ter o maven instalado na maquina.

comando para geração do jar:

```javascript
mvn clean install
```

Após isso é preciso que o arquivo CSV que se deseja processar esteja na mesma pasta do .jar. Para execução do jar é necessário
ter alguma versão superior a 8 do java instalada. Com o java instalado e com ambos arquivos na mesma pasta basta abrir o
prompt de comando e executar: 

código para execução do processo: 

```javascript
java -jar nome-arquivo-jar.jar nome-arquivo-csv.csv
```

Após a execução do jar o arquivo informado vai ser processado e sera feita a geração de um
novo arquivo CSV chamado resultado com os respectivos resultados retornados pelo Banco central.

OBS: Para facilitar os testes foram incluidos um arquivo jar e um arquivo csv na base do projeto 
</br></br>

<p align="center">
  <a href="https://www.linkedin.com/in/gabrielnunesfaria/">
    <img alt="LinkedIn" src="https://img.shields.io/badge/LinkedIn-Gabriel%20Faria-blue?style=flat-square&logo=linkedin">
  </a>
  <a href="mailto:gabrielnunesfariapta@hotmail.com">
    <img alt="Email" src="https://img.shields.io/badge/Email-gabrielnunesfariapta@hotmail.com-blue?style=flat-square&logo=appveyor">
  </a>
</p>