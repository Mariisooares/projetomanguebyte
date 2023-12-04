package com.mangueByte.telemetria.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mangueByte.telemetria.domain.model.Dados;
import com.mangueByte.telemetria.repository.DadosRepository;
import com.mangueByte.telemetria.services.DadosService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@SpringBootApplication
@RestController
@RequestMapping(value = "/dados")
public class DadosController {

    @Autowired
    private DadosService dadosService;
    @Autowired
    private DadosRepository dadosRepository;

    @PostConstruct
    public void carregarDadosAoIniciar() {
        criarDiretorioSeNaoExistir();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dados.ser"))) {
            List<Dados> dados = (List<Dados>) ois.readObject();
            dadosRepository.saveAll(dados);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); 
        }
    }

    @PreDestroy
    public void salvarDadosAntesDeFechar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dados.ser"))) {
            oos.writeObject(dadosRepository.findAll()); 
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    private void criarDiretorioSeNaoExistir() {
        File directory = new File("src/main/java/com/mangueByte/telemetria/files");
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Diretório criado com sucesso: " + directory.getAbsolutePath());
            } else {
                System.err.println("Falha ao criar o diretório: " + directory.getAbsolutePath());
            }
        }
    }

    @GetMapping("/listar")
    public List<Dados> findAll() {
        List<Dados> result = dadosService.findAll();
        return result;
    }

    @DeleteMapping("/{id}")
    public String deletarDados(@PathVariable Long id) {
        try {
            dadosService.deletarDados(id);
            return "Registro deletado com sucesso.";
        } catch (Exception e) {
            return "Falha ao deletar o registro com o ID: " + id;
        }
    }

    @PostMapping("/processar")
    public String processarArquivoTxt(@RequestPart("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            Files.write(Paths.get("./src/main/java/com/mangueByte/telemetria/files/" + fileName),
                    file.getBytes());

            int leftTurns = 0;
            int rightTurns = 0;
            int centerTurns = 0;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] tokens = line.split(" ");

                    for (String token : tokens) {
                        try {
                            int value = Integer.parseInt(token);
                            if (value >= 0 && value <= 3400) {
                                leftTurns++;
                            } else if (value >= 3600 && value <= 7000) {
                                rightTurns++;
                            } else {
                                centerTurns++;
                            }
                        } catch (NumberFormatException e) {
                        
                        }
                    }
                }
            }

            Dados dados = new Dados();
            dados.setCurvaEsquerda(leftTurns);
            dados.setCurvaDireita(rightTurns);
            dados.setTotalCurvas(centerTurns + leftTurns + rightTurns);
            dadosRepository.save(dados);

            return "Dados salvos com sucesso para o arquivo: " + fileName;

        } catch (IOException e) {
            return "Falha ao receber o arquivo: " + fileName;
        }
    }
}
