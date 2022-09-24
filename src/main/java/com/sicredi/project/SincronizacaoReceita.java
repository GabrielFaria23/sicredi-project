/*
Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de contas para enviar ao Banco Central..
Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal, antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma nova coluna.


Formato CSV:
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B
3202;00321-2;34500,00;B
...

*/
package com.sicredi.project;
import com.opencsv.CSVWriter;
import com.sicredi.project.service.ReceitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SincronizacaoReceita implements CommandLineRunner {

	@Autowired
	private ReceitaService receitaService;

	public static void main(String[] args) {

		SpringApplication.run(SincronizacaoReceita.class, args);
		// Exemplo como chamar o "serviço" do Banco Central.
		// ReceitaService receitaService = new ReceitaService();
		// receitaService.atualizarConta("0101", "123456", 100.50, "A");
	}

	@Override
	public void run(String... args) throws Exception {

		Logger log = LoggerFactory.getLogger(SincronizacaoReceita.class);

		final String diretorioPastaAtual = System.getProperty("user.dir");

		final String diretorioCompleto = diretorioPastaAtual+"\\"+"test.csv"/*args[0]*/ ;

		List<String> resultadoLinhas = new ArrayList<>();

		System.out.println(diretorioCompleto);

		try (BufferedReader br = new BufferedReader(new FileReader(diretorioCompleto))){

			log.info("Iniciando leitura do arquivo!");

			String linha = br.readLine();
			resultadoLinhas.add(linha.trim()+";resultado");
			linha = br.readLine();
			while (linha != null){

				String[] columns = linha.split(";");

				boolean resultado = receitaService.atualizarConta(columns[0], columns[1].replace("-",""), columns[2] != null ? Double.parseDouble(columns[2].replace(",",".")) : null, columns.length == 4 ? columns[3] : null);

				resultadoLinhas.add(linha.trim()+((resultado) ? ";aceito" : ";nao aceito"));

				linha = br.readLine();
			}

			log.info("Leitura do arquivo finalizada!");

		}catch (IOException e){
			System.out.println("Error: "+ e.getMessage());
		}

		String novoArquivo = diretorioPastaAtual+"/resultado.csv";

		try (FileWriter fw = new FileWriter(novoArquivo)){

			log.info("Iniciando criacao do arquivo resultante!");

			CSVWriter csvw = new CSVWriter(fw);

			List<String[]> linhasNovoArquivo = resultadoLinhas.stream().map(rl -> rl.split(";")).collect(Collectors.toList());

			csvw.writeAll(linhasNovoArquivo);

			log.info("Criacao do arquivo finalizada!");

			csvw.close();

		}catch (IOException e){
			System.out.println("Error: "+ e.getMessage());
		}

	}

}