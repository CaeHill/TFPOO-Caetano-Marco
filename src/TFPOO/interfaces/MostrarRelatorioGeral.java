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

public class MostrarRelatorioGeral {
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

    public MostrarRelatorioGeral(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TransporteGestor transporteGestor = SistemaGestores.getTransporteGestor();
        DroneGestor droneGestor = SistemaGestores.getDroneGestor();

        TableView<Transporte> tabelaTransportes = criarTabelaTransportes(transporteGestor);

        TableView<Drone> tabelaDrones = criarTabelaDrones(droneGestor);

        HBox botoesAcao = new HBox(15);
        botoesAcao.setAlignment(Pos.CENTER);
        botoesAcao.setPadding(new Insets(10));

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));

        btnVoltar.setOnAction(e -> {
            primaryStage.setScene(previousScene);
        });

        botoesAcao.getChildren().addAll(btnVoltar);

        layout.getChildren().addAll(
                new Label("Relatório Geral: Transportes e Drones"),
                new Label("Transportes"),
                tabelaTransportes,
                new Label("Drones"),
                tabelaDrones,
                botoesAcao
        );

        Scene scene = new Scene(layout, 1200, 800);
        primaryStage.setTitle("Relatório Geral");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Drone> criarTabelaDrones(DroneGestor droneGestor) {
        TableView<Drone> tabelaDrones = new TableView<>();

        TableColumn<Drone, Integer> colunaCodigo = new TableColumn<>("Código");
        colunaCodigo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCodigo()).asObject());

        TableColumn<Drone, Double> colunaCustoFixo = new TableColumn<>("Custo Fixo");
        colunaCustoFixo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getCustoFixo()).asObject());

        TableColumn<Drone, Double> colunaAutonomia = new TableColumn<>("Autonomia");
        colunaAutonomia.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getAutonomia()).asObject());

        TableColumn<Drone, String> colunaEspecifica = new TableColumn<>("Específica");
        colunaEspecifica.setCellValueFactory(cellData -> {
            Drone d = cellData.getValue();
            if (d instanceof DronePessoal) {
                return new javafx.beans.property.SimpleStringProperty(
                        "Qtd Pessoas: " + ((DronePessoal) d).getQtdMaxPessoas()
                );
            } else if (d instanceof DroneCargaInanimada) {
                DroneCargaInanimada cargaInanimada = (DroneCargaInanimada) d;
                return new javafx.beans.property.SimpleStringProperty(
                        "Peso Máx: " + cargaInanimada.getPesoMaximo() +
                                ", Proteção: " + cargaInanimada.isProtecao()
                );
            } else if (d instanceof DroneCargaViva) {
                DroneCargaViva cargaViva = (DroneCargaViva) d;
                return new javafx.beans.property.SimpleStringProperty(
                        "Peso Máx: " + cargaViva.getPesoMaximo() +
                                ", Climatizado: " + cargaViva.isClimatizado()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        TableColumn<Drone, Double> colunaCustoKm = new TableColumn<>("Custo por Km");
        colunaCustoKm.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().calculaCustoKm()).asObject());

        tabelaDrones.getColumns().addAll(
                colunaCodigo, colunaCustoFixo, colunaAutonomia, colunaEspecifica, colunaCustoKm
        );

        tabelaDrones.getItems().setAll(droneGestor.getDrones());
        return tabelaDrones;
    }

    private TableView<Transporte> criarTabelaTransportes(TransporteGestor transporteGestor) {
        TableView<Transporte> tabelaTransportes = new TableView<>();

        TableColumn<Transporte, Integer> colunaNumero = new TableColumn<>("Número");
        colunaNumero.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());

        TableColumn<Transporte, String> colunaCliente = new TableColumn<>("Cliente");
        colunaCliente.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNomeCliente()));

        TableColumn<Transporte, String> colunaDescricao = new TableColumn<>("Descrição");
        colunaDescricao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescricao()));

        TableColumn<Transporte, Double> colunaPeso = new TableColumn<>("Peso");
        colunaPeso.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());

        TableColumn<Transporte, Double> colunaLatitudeOrigem = new TableColumn<>("Lat. Origem");
        colunaLatitudeOrigem.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLatitudeOrigem()).asObject());

        TableColumn<Transporte, Double> colunaLatitudeDestino = new TableColumn<>("Lat. Destino");
        colunaLatitudeDestino.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLatitudeDestino()).asObject());

        TableColumn<Transporte, Double> colunaLongitudeOrigem = new TableColumn<>("Long. Origem");
        colunaLongitudeOrigem.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLongitudeOrigem()).asObject());

        TableColumn<Transporte, Double> colunaLongitudeDestino = new TableColumn<>("Long. Destino");
        colunaLongitudeDestino.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getLongitudeDestino()).asObject());

        TableColumn<Transporte, Estado> colunaSituacao = new TableColumn<>("Situação");
        colunaSituacao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSituacao()));

        TableColumn<Transporte, String> colunaEspecifica = new TableColumn<>("Específica");
        colunaEspecifica.setCellValueFactory(cellData -> {
            Transporte t = cellData.getValue();
            if (t instanceof TransportePessoal) {
                return new javafx.beans.property.SimpleStringProperty(String.valueOf(((TransportePessoal) t).getQtdPassageiros()));
            } else if (t instanceof TransporteCargaInanimada) {
                return new javafx.beans.property.SimpleStringProperty(String.valueOf(((TransporteCargaInanimada) t).isCargaPerigosa()));
            } else if (t instanceof TransporteCargaViva) {
                TransporteCargaViva cargaViva = (TransporteCargaViva) t;
                return new javafx.beans.property.SimpleStringProperty(
                        "Temp Min: " + cargaViva.getTemperaturaMinima() + ", Temp Max: " + cargaViva.getTemperaturaMaxima());
            }
            return new javafx.beans.property.SimpleStringProperty("");
        });

        TableColumn<Transporte, Double> colunaCusto = new TableColumn<>("Custo");
        colunaCusto.setCellValueFactory(cellData -> {
            Transporte t = cellData.getValue();
            if (t.getSituacao() == Estado.ALOCADO) {
                return new javafx.beans.property.SimpleDoubleProperty(t.calcularCusto()).asObject();
            }
            return new javafx.beans.property.SimpleDoubleProperty(0).asObject();
        });

        tabelaTransportes.getColumns().addAll(
                colunaNumero, colunaCliente, colunaDescricao, colunaPeso,
                colunaLatitudeOrigem, colunaLatitudeDestino, colunaLongitudeOrigem, colunaLongitudeDestino,
                colunaSituacao, colunaEspecifica, colunaCusto
        );

        tabelaTransportes.getItems().setAll(transporteGestor.getTransportesPendentes());

        return tabelaTransportes;
    }
}