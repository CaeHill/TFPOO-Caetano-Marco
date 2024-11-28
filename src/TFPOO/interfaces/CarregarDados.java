package TFPOO.interfaces;

import TFPOO.dados.*;
import TFPOO.gestores.SistemaGestores;
import TFPOO.gestores.DroneGestor;
import TFPOO.gestores.TransporteGestor;
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

public class CarregarDados {
    private Stage primaryStage;
    private Scene previousScene;

    private final String BUTTON_STYLE = """
            -fx-background-color: #1976D2FF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 150px;
            """;
    private final String BUTTON_HOVER_STYLE = """
            -fx-background-color: rgba(25,118,210,0.75);
            -fx-cursor: hand;
            """;

    public CarregarDados(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        DroneGestor droneGestor = SistemaGestores.getDroneGestor();
        TransporteGestor transporteGestor = SistemaGestores.getTransporteGestor();

        TextField txtNomeArquivo = new TextField();
        txtNomeArquivo.setPromptText("Nome base para os arquivos (sem extensão)");

        Button btnCarregar = new Button("Carregar Dados");
        btnCarregar.setStyle(BUTTON_STYLE);
        btnCarregar.setOnMouseEntered(e -> btnCarregar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnCarregar.setOnMouseExited(e -> btnCarregar.setStyle(BUTTON_STYLE));

        btnCarregar.setOnAction(e -> {
            String nomeBase = txtNomeArquivo.getText().trim();
            if (nomeBase.isEmpty()) {
                mostrarAlerta("Erro", "O nome do arquivo não pode estar vazio.");
                return;
            }

            boolean sucessoDrones = carregarDrones(droneGestor, nomeBase + "_drones.csv");
            boolean sucessoTransportes = carregarTransportes(transporteGestor, nomeBase + "_transportes.csv");

            if (sucessoDrones && sucessoTransportes) {
                mostrarAlerta("Sucesso", "Dados carregados com sucesso dos arquivos:\n" +
                        nomeBase + "_drones.csv\n" + nomeBase + "_transportes.csv");
            } else {
                mostrarAlerta("Erro", "Ocorreu um problema ao carregar os dados.");
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));

        btnVoltar.setOnAction(e -> {
            primaryStage.setScene(previousScene);
        });

        HBox botoesAcao = new HBox(15, btnCarregar, btnVoltar);
        botoesAcao.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                new Label("Carregar Dados do Sistema"),
                txtNomeArquivo,
                botoesAcao
        );

        Scene scene = new Scene(layout, 600, 200);
        primaryStage.setTitle("Carregar Dados");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean carregarDrones(DroneGestor droneGestor, String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                String tipo = campos[0];
                int codigo = Integer.parseInt(campos[1]);
                double custoFixo = Double.parseDouble(campos[2]);
                double autonomia = Double.parseDouble(campos[3]);
                String[] especificos = campos[4].split(",");

                Drone drone = null;
                if (tipo.equals("Drone Pessoal")) {
                    int qtdMaxPessoas = Integer.parseInt(especificos[0].split(":")[1]);
                    drone = new DronePessoal(codigo, custoFixo, autonomia, qtdMaxPessoas);
                } else if (tipo.equals("Drone Carga Inanimada")) {
                    double pesoMaximo = Double.parseDouble(especificos[0].split(":")[1]);
                    boolean protecao = Boolean.parseBoolean(especificos[1].split(":")[1]);
                    drone = new DroneCargaInanimada(codigo, custoFixo, autonomia, pesoMaximo, protecao);
                } else if (tipo.equals("Drone Carga Viva")) {
                    double pesoMaximo = Double.parseDouble(especificos[0].split(":")[1]);
                    boolean climatizado = Boolean.parseBoolean(especificos[1].split(":")[1]);
                    drone = new DroneCargaViva(codigo, custoFixo, autonomia, pesoMaximo, climatizado);
                }

                if (drone != null) {
                    droneGestor.cadastrarDrone(drone);
                }
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean carregarTransportes(TransporteGestor transporteGestor, String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = reader.readLine(); // Ignora o cabeçalho
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(";");
                int numero = Integer.parseInt(campos[0]);
                String nomeCliente = campos[1];
                String descricao = campos[2];
                double peso = Double.parseDouble(campos[3]);
                double latOrigem = Double.parseDouble(campos[4]);
                double latDestino = Double.parseDouble(campos[5]);
                double longOrigem = Double.parseDouble(campos[6]);
                double longDestino = Double.parseDouble(campos[7]);
                Estado situacao = Estado.valueOf(campos[8]);
                String[] especificos = campos[9].split(",");

                Transporte transporte = null;
                if (especificos[0].startsWith("qtdPassageiros")) {
                    int qtdPassageiros = Integer.parseInt(especificos[0].split(":")[1]);
                    transporte = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, qtdPassageiros);
                } else if (especificos[0].startsWith("cargaPerigosa")) {
                    boolean cargaPerigosa = Boolean.parseBoolean(especificos[0].split(":")[1]);
                    transporte = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, cargaPerigosa);
                } else if (especificos[0].startsWith("tempMin")) {
                    double tempMin = Double.parseDouble(especificos[0].split(":")[1]);
                    double tempMax = Double.parseDouble(especificos[1].split(":")[1]);
                    transporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, latDestino, longOrigem, longDestino, tempMin, tempMax);
                }

                if (transporte != null) {
                    transporte.setSituacao(situacao);
                    transporteGestor.adicionarTransporte(transporte);
                }
            }
            return true;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return false;
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