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

import java.io.FileWriter;
import java.io.IOException;

public class SalvarDados {
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

    public SalvarDados(Scene previousScene) {
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

        Button btnSalvar = new Button("Salvar Dados");
        btnSalvar.setStyle(BUTTON_STYLE);
        btnSalvar.setOnMouseEntered(e -> btnSalvar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnSalvar.setOnMouseExited(e -> btnSalvar.setStyle(BUTTON_STYLE));

        btnSalvar.setOnAction(e -> {
            String nomeBase = txtNomeArquivo.getText().trim();
            if (nomeBase.isEmpty()) {
                mostrarAlerta("Erro", "O nome do arquivo não pode estar vazio.");
                return;
            }

            boolean sucessoDrones = salvarDrones(droneGestor, nomeBase + "_drones.csv");
            boolean sucessoTransportes = salvarTransportes(transporteGestor, nomeBase + "_transportes.csv");

            if (sucessoDrones && sucessoTransportes) {
                mostrarAlerta("Sucesso", "Dados salvos com sucesso nos arquivos:\n" +
                        nomeBase + "_drones.csv\n" + nomeBase + "_transportes.csv");
            } else {
                mostrarAlerta("Erro", "Ocorreu um problema ao salvar os dados.");
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));

        btnVoltar.setOnAction(e -> {
            primaryStage.setScene(previousScene);
        });

        HBox botoesAcao = new HBox(15, btnSalvar, btnVoltar);
        botoesAcao.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                new Label("Salvar Dados do Sistema"),
                txtNomeArquivo,
                botoesAcao
        );

        Scene scene = new Scene(layout, 600, 200);
        primaryStage.setTitle("Salvar Dados");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean salvarDrones(DroneGestor droneGestor, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("tipo;codigo;custofixo;autonomia;atributos_especificos\n");
            for (Drone drone : droneGestor.getDrones()) {
                String tipo = getTipoDrone(drone);
                String especificos = getAtributosEspecificosDrone(drone);
                writer.write(tipo + ";" +
                        drone.getCodigo() + ";" +
                        drone.getCustoFixo() + ";" +
                        drone.getAutonomia() + ";" +
                        especificos + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getTipoDrone(Drone drone) {
        if (drone instanceof DronePessoal) return "Drone Pessoal";
        if (drone instanceof DroneCargaInanimada) return "Drone Carga Inanimada";
        if (drone instanceof DroneCargaViva) return "Drone Carga Viva";
        return "Desconhecido";
    }

    private String getAtributosEspecificosDrone(Drone drone) {
        if (drone instanceof DronePessoal) {
            return "qtdMaxPessoas:" + ((DronePessoal) drone).getQtdMaxPessoas();
        }
        if (drone instanceof DroneCargaInanimada) {
            return "pesoMaximo:" + ((DroneCargaInanimada) drone).getPesoMaximo() +
                    ",protecao:" + ((DroneCargaInanimada) drone).isProtecao();
        }
        if (drone instanceof DroneCargaViva) {
            return "pesoMaximo:" + ((DroneCargaViva) drone).getPesoMaximo() +
                    ",climatizado:" + ((DroneCargaViva) drone).isClimatizado();
        }
        return "";
    }

    private boolean salvarTransportes(TransporteGestor transporteGestor, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("numero;nomeCliente;descricao;peso;latorigem;latdestino;longorigem;longdestino;situacao;atributos_especificos\n");
            for (Transporte transporte : transporteGestor.getTodosTransportes()) {
                String especificos = getAtributosEspecificosTransporte(transporte);
                writer.write(transporte.getNumero() + ";" +
                        transporte.getNomeCliente() + ";" +
                        transporte.getDescricao() + ";" +
                        transporte.getPeso() + ";" +
                        transporte.getLatitudeOrigem() + ";" +
                        transporte.getLatitudeDestino() + ";" +
                        transporte.getLongitudeOrigem() + ";" +
                        transporte.getLongitudeDestino() + ";" +
                        transporte.getSituacao() + ";" +
                        especificos + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getAtributosEspecificosTransporte(Transporte transporte) {
        if (transporte instanceof TransportePessoal) {
            return "qtdPassageiros:" + ((TransportePessoal) transporte).getQtdPassageiros();
        }
        if (transporte instanceof TransporteCargaInanimada) {
            return "cargaPerigosa:" + ((TransporteCargaInanimada) transporte).isCargaPerigosa();
        }
        if (transporte instanceof TransporteCargaViva) {
            return "tempMin:" + ((TransporteCargaViva) transporte).getTemperaturaMinima() +
                    ",tempMax:" + ((TransporteCargaViva) transporte).getTemperaturaMaxima();
        }
        return "";
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}