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

public class AlterarASituacaoDeUmTransporte {
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

    public AlterarASituacaoDeUmTransporte(Scene previousScene) {
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

        // Campo para entrada do número do transporte
        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Número do Transporte");

        Button btnBuscar = new Button("Buscar Transporte");
        btnBuscar.setStyle(BUTTON_STYLE);
        btnBuscar.setOnMouseEntered(e -> btnBuscar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnBuscar.setOnMouseExited(e -> btnBuscar.setStyle(BUTTON_STYLE));

        Label lblDetalhes = new Label();
        ComboBox<Estado> comboSituacoes = new ComboBox<>();
        comboSituacoes.setDisable(true);

        Button btnAlterar = new Button("Alterar Situação");
        btnAlterar.setStyle(BUTTON_STYLE);
        btnAlterar.setDisable(true);

        btnBuscar.setOnAction(e -> {
            lblDetalhes.setText(""); // Limpa mensagens anteriores
            btnAlterar.setDisable(true);
            comboSituacoes.setDisable(true);

            String numeroStr = txtNumero.getText().trim();
            if (numeroStr.isEmpty()) {
                mostrarAlerta("Erro", "Insira um número de transporte válido.");
                return;
            }

            try {
                int numero = Integer.parseInt(numeroStr);
                Transporte transporte = transporteGestor.getTodosTransportes().stream()
                        .filter(t -> t.getNumero() == numero)
                        .findFirst()
                        .orElse(null);

                if (transporte == null) {
                    mostrarAlerta("Erro", "Transporte com número " + numero + " não encontrado.");
                    return;
                }

                if (transporte.getSituacao() == Estado.TERMINADO || transporte.getSituacao() == Estado.CANCELADO) {
                    mostrarAlerta("Erro", "Transporte já está em estado TERMINADO ou CANCELADO e não pode ser alterado.");
                    return;
                }

                // Mostra os detalhes do transporte
                lblDetalhes.setText("Transporte " + transporte.getNumero() + " - " + transporte.getNomeCliente() +
                        "\nSituação atual: " + transporte.getSituacao());

                // Configura o ComboBox com as situações disponíveis
                comboSituacoes.getItems().clear();
                if (transporte.getSituacao() == Estado.PENDENTE) {
                    comboSituacoes.getItems().addAll(Estado.ALOCADO, Estado.CANCELADO, Estado.TERMINADO);
                } else if (transporte.getSituacao() == Estado.ALOCADO) {
                    comboSituacoes.getItems().addAll(Estado.CANCELADO, Estado.TERMINADO);
                }

                comboSituacoes.setDisable(false);
                btnAlterar.setDisable(false);

                btnAlterar.setOnAction(event -> {
                    Estado novaSituacao = comboSituacoes.getValue();
                    if (novaSituacao == null) {
                        mostrarAlerta("Erro", "Selecione uma nova situação para o transporte.");
                        return;
                    }

                    if (novaSituacao == Estado.ALOCADO && transporte.getSituacao() == Estado.PENDENTE) {
                        // Tenta alocar um drone para o transporte
                        boolean droneAlocado = droneGestor.alocarDroneParaTransporte(transporte);
                        if (!droneAlocado) {
                            mostrarAlerta("Erro", "Não há drones disponíveis compatíveis com o transporte.");
                            return;
                        }
                    }

                    // Atualiza a situação do transporte
                    transporte.setSituacao(novaSituacao);

                    // Atualiza a fila de pendentes no transporteGestor
                    transporteGestor.atualizarFilaPendentes();

                    mostrarAlerta("Sucesso", "Situação do transporte alterada para: " + novaSituacao);
                    lblDetalhes.setText("Transporte " + transporte.getNumero() + " - " + transporte.getNomeCliente() +
                            "\nSituação atual: " + transporte.getSituacao());
                    comboSituacoes.setDisable(true);
                    btnAlterar.setDisable(true);
                });

            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Número de transporte inválido.");
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));

        btnVoltar.setOnAction(e -> {
            primaryStage.setScene(previousScene);
        });

        HBox botoesAcao = new HBox(15, btnBuscar, btnVoltar);
        botoesAcao.setAlignment(Pos.CENTER);

        VBox detalhesLayout = new VBox(10, lblDetalhes, comboSituacoes, btnAlterar);
        detalhesLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(
                new Label("Alterar Situação de um Transporte"),
                txtNumero,
                botoesAcao,
                detalhesLayout
        );

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setTitle("Alterar Situação de um Transporte");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}