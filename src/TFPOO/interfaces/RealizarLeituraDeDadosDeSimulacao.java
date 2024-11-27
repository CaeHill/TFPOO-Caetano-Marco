package TFPOO.interfaces;

import TFPOO.dados.*;
import TFPOO.gestores.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RealizarLeituraDeDadosDeSimulacao {
    private Stage primaryStage;

    private final String BUTTON_STYLE = """
            -fx-background-color: #1976D2FF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 150px;
            """;
    private final String BUTTON_DISABLED_STYLE = """
            -fx-background-color: #A9A9A9;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 150px;
            """;
    private final String BUTTON_HOVER_STYLE = """
            -fx-background-color: rgba(25,118,210,0.75);
            -fx-cursor: hand;
            """;

    public RealizarLeituraDeDadosDeSimulacao() {
        // A instância global de DroneGestor e TransporteGestor será acessada diretamente
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField txtNomeArquivo = new TextField();
        txtNomeArquivo.setPromptText("Digite o nome do arquivo (sem extensão)");

        Button btnCarregarDados = new Button("Carregar Dados");
        btnCarregarDados.setStyle(BUTTON_DISABLED_STYLE);
        btnCarregarDados.setDisable(true);

        btnCarregarDados.setOnAction(e -> {
            String nomeArquivo = txtNomeArquivo.getText().trim();
            if (!nomeArquivo.isEmpty()) {
                carregarDadosDeSimulacao(nomeArquivo);
            } else {
                mostrarAlerta("Erro", "Por favor, insira o nome do arquivo.");
            }
        });

        // Habilitar o botão quando o campo for preenchido
        txtNomeArquivo.textProperty().addListener((observable, oldValue, newValue) -> {
            btnCarregarDados.setDisable(newValue.trim().isEmpty());
            btnCarregarDados.setStyle(BUTTON_STYLE);
            btnCarregarDados.setOnMouseEntered(e -> btnCarregarDados.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
            btnCarregarDados.setOnMouseExited(e -> btnCarregarDados.setStyle(BUTTON_STYLE));
            btnCarregarDados.setDisable(false);
        });

        HBox botoesAcao = new HBox(15);
        botoesAcao.setAlignment(Pos.CENTER);
        botoesAcao.setPadding(new Insets(10));

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));
        btnVoltar.setOnAction(e -> {
            Menu menu = new Menu();
            menu.start(primaryStage); // Voltar para a tela do menu
        });

        botoesAcao.getChildren().add(btnVoltar);

        layout.getChildren().addAll(txtNomeArquivo, btnCarregarDados, botoesAcao);

        Scene scene = new Scene(layout, 500, 200);
        primaryStage.setTitle("Realizar Leitura de Dados de Simulação");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void carregarDadosDeSimulacao(String nomeArquivo) {
        // Acessando os gestores globais diretamente
        DroneGestor droneGestor = SistemaGestores.getDroneGestor();
        TransporteGestor transporteGestor = SistemaGestores.getTransporteGestor();

        // Definindo os caminhos dos arquivos CSV
        String caminhoDrones = nomeArquivo + "-DRONES.CSV";
        String caminhoTransportes = nomeArquivo + "-TRANSPORTES.CSV";

        // Flags para verificar se algum objeto foi cadastrado
        boolean algumDroneCadastrado = false;
        boolean algumTransporteCadastrado = false;

        // Carregar os dados de drones
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoDrones))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados[0].equals("1")) { // Drone Pessoal
                    DronePessoal drone = new DronePessoal(
                            Integer.parseInt(dados[1]),
                            Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]),
                            Integer.parseInt(dados[4])
                    );
                    droneGestor.cadastrarDrone(drone);  // Usando o gestor global
                    algumDroneCadastrado = true;
                } else if (dados[0].equals("2")) { // Drone Carga Inanimada
                    DroneCargaInanimada drone = new DroneCargaInanimada(
                            Integer.parseInt(dados[1]),
                            Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]),
                            Double.parseDouble(dados[4]),
                            Boolean.parseBoolean(dados[5])
                    );
                    droneGestor.cadastrarDrone(drone);  // Usando o gestor global
                    algumDroneCadastrado = true;
                } else if (dados[0].equals("3")) { // Drone Carga Viva
                    DroneCargaViva drone = new DroneCargaViva(
                            Integer.parseInt(dados[1]),
                            Double.parseDouble(dados[2]),
                            Double.parseDouble(dados[3]),
                            Double.parseDouble(dados[4]),
                            Boolean.parseBoolean(dados[5])
                    );
                    droneGestor.cadastrarDrone(drone);  // Usando o gestor global
                    algumDroneCadastrado = true;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao ler o arquivo de drones.");
        }

        // Carregar os dados de transportes
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoTransportes))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados[0].equals("1")) { // Transporte Pessoal
                    TransportePessoal transporte = new TransportePessoal(
                            Integer.parseInt(dados[1]),
                            dados[2],
                            dados[3],
                            Double.parseDouble(dados[4]),
                            Double.parseDouble(dados[5]),
                            Double.parseDouble(dados[6]),
                            Double.parseDouble(dados[7]),
                            Double.parseDouble(dados[8]),
                            Integer.parseInt(dados[9])
                    );
                    transporteGestor.cadastrarTransporte(transporte);  // Usando o gestor global
                    algumTransporteCadastrado = true;
                } else if (dados[0].equals("2")) { // Transporte Carga Inanimada
                    TransporteCargaInanimada transporte = new TransporteCargaInanimada(
                            Integer.parseInt(dados[1]),
                            dados[2],
                            dados[3],
                            Double.parseDouble(dados[4]),
                            Double.parseDouble(dados[5]),
                            Double.parseDouble(dados[6]),
                            Double.parseDouble(dados[7]),
                            Double.parseDouble(dados[8]),
                            Boolean.parseBoolean(dados[9])
                    );
                    transporteGestor.cadastrarTransporte(transporte);  // Usando o gestor global
                    algumTransporteCadastrado = true;
                } else if (dados[0].equals("3")) { // Transporte Carga Viva
                    TransporteCargaViva transporte = new TransporteCargaViva(
                            Integer.parseInt(dados[1]),
                            dados[2],
                            dados[3],
                            Double.parseDouble(dados[4]),
                            Double.parseDouble(dados[5]),
                            Double.parseDouble(dados[6]),
                            Double.parseDouble(dados[7]),
                            Double.parseDouble(dados[8]),
                            Double.parseDouble(dados[9]),
                            Double.parseDouble(dados[10])
                    );
                    transporteGestor.cadastrarTransporte(transporte);  // Usando o gestor global
                    algumTransporteCadastrado = true;
                }
            }
        } catch (IOException e) {
            mostrarAlerta("Erro", "Falha ao ler o arquivo de transportes.");
        }

        if (algumDroneCadastrado || algumTransporteCadastrado) {
            MostrarRelatorioGeral relatorio = new MostrarRelatorioGeral(primaryStage.getScene());  // Passando a cena atual (Leitura)
            relatorio.setPrimaryStage(primaryStage);
            relatorio.mostrarTela();
        } else {
            mostrarAlerta("Aviso", "Nenhum dado foi cadastrado.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}