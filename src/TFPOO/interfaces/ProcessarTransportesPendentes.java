package TFPOO.interfaces;

import TFPOO.dados.*;
import TFPOO.gestores.SistemaGestores;
import TFPOO.gestores.TransporteGestor;
import TFPOO.gestores.DroneGestor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProcessarTransportesPendentes {
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

    public ProcessarTransportesPendentes(Scene previousScene) {
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

        TableView<Transporte> tabelaTransportes = criarTabelaTransportesPendentes(transporteGestor);

        HBox botoesAcao = new HBox(15);
        botoesAcao.setAlignment(Pos.CENTER);
        botoesAcao.setPadding(new Insets(10));

        Button btnProcessar = new Button("Processar Transportes");
        btnProcessar.setStyle(BUTTON_STYLE);
        btnProcessar.setOnMouseEntered(e -> btnProcessar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnProcessar.setOnMouseExited(e -> btnProcessar.setStyle(BUTTON_STYLE));

        btnProcessar.setOnAction(e -> {
            if (transporteGestor.getTransportesPendentes().isEmpty()) {
                mostrarAlerta("Erro", "Não há transportes pendentes para processar.");
            } else {
                transporteGestor.processarTransportesPendentes();
                tabelaTransportes.refresh();
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));

        btnVoltar.setOnAction(e -> {
            primaryStage.setScene(previousScene);
        });

        botoesAcao.getChildren().addAll(btnProcessar, btnVoltar);

        layout.getChildren().addAll(
                new Label("Processar Transportes Pendentes"),
                tabelaTransportes,
                botoesAcao
        );

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Processar Transportes Pendentes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Transporte> criarTabelaTransportesPendentes(TransporteGestor transporteGestor) {
        TableView<Transporte> tabelaTransportes = new TableView<>();

        TableColumn<Transporte, Integer> colunaNumero = new TableColumn<>("Número");
        colunaNumero.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getNumero()).asObject());

        TableColumn<Transporte, String> colunaCliente = new TableColumn<>("Cliente");
        colunaCliente.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNomeCliente()));

        TableColumn<Transporte, Estado> colunaSituacao = new TableColumn<>("Situação");
        colunaSituacao.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getSituacao()));

        tabelaTransportes.getColumns().addAll(colunaNumero, colunaCliente, colunaSituacao);

        tabelaTransportes.getItems().setAll(transporteGestor.getTransportesPendentes());

        return tabelaTransportes;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
