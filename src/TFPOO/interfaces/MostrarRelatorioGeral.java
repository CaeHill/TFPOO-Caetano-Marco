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

public class MostrarRelatorioGeral {
    private Stage primaryStage;
    private Stage previousStage; // Tela anterior (de onde viemos)

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

    // Construtor agora recebe a tela anterior para redirecionamento do botão "Voltar"
    public MostrarRelatorioGeral(Stage previousStage) {
        this.previousStage = previousStage;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Acessando os gestores globais
        DroneGestor droneGestor = SistemaGestores.getDroneGestor();
        TransporteGestor transporteGestor = SistemaGestores.getTransporteGestor();

        // Tabela para exibir Drones
        TableView<Drone> tabelaDrones = criarTabelaDrones(droneGestor);

        // Tabela para exibir Transportes
        TableView<Transporte> tabelaTransportes = criarTabelaTransportes(transporteGestor);

        // Botões de ação
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

        layout.getChildren().addAll(
                new Label("Relatório de Drones Cadastrados"),
                tabelaDrones,
                new Label("Relatório de Transportes Cadastrados"),
                tabelaTransportes,
                botoesAcao
        );

        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setTitle("Mostrar Relatório Geral");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Drone> criarTabelaDrones(DroneGestor droneGestor) {
        TableView<Drone> tabelaDrones = new TableView<>();

        // Definindo as colunas
        TableColumn<Drone, Integer> colunaCodigo = new TableColumn<>("Código");
        colunaCodigo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCodigo()).asObject());

        TableColumn<Drone, Double> colunaCustoFixo = new TableColumn<>("Custo Fixo");
        colunaCustoFixo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getCustoFixo()).asObject());

        TableColumn<Drone, Double> colunaAutonomia = new TableColumn<>("Autonomia");
        colunaAutonomia.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getAutonomia()).asObject());

        tabelaDrones.getColumns().addAll(colunaCodigo, colunaCustoFixo, colunaAutonomia);

        // Adicionando os drones cadastrados
        tabelaDrones.getItems().setAll(droneGestor.getDrones());

        return tabelaDrones;
    }

    private TableView<Transporte> criarTabelaTransportes(TransporteGestor transporteGestor) {
        TableView<Transporte> tabelaTransportes = new TableView<>();

        // Definindo as colunas
        TableColumn<Transporte, Integer> colunaNumero = new TableColumn<>("Número");
        colunaNumero.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());

        TableColumn<Transporte, String> colunaCliente = new TableColumn<>("Cliente");
        colunaCliente.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNomeCliente()));

        TableColumn<Transporte, Double> colunaPeso = new TableColumn<>("Peso");
        colunaPeso.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPeso()).asObject());

        TableColumn<Transporte, String> colunaSituacao = new TableColumn<>("Situação");
        colunaSituacao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSituacao().toString()));

        tabelaTransportes.getColumns().addAll(colunaNumero, colunaCliente, colunaPeso, colunaSituacao);

        // Adicionando os transportes cadastrados
        tabelaTransportes.getItems().setAll(transporteGestor.getTransportes());

        return tabelaTransportes;
    }
}