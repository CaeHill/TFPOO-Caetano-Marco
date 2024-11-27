package TFPOO.interfaces;

import TFPOO.gestores.DroneGestor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarNovoDrone {

    private Stage primaryStage;
    private TextArea txtMensagens;

    private final String BUTTON_STYLE = """
            -fx-background-color: #1976D2FF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 200px;
            """;
    private final String BUTTON_HOVER_STYLE = """
            -fx-background-color: rgba(25,118,210,0.75);
            -fx-cursor: hand;
            """;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void mostrarTela() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        Label titulo = new Label("Cadastro de Drone");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox dadosPanel = new VBox(15);
        dadosPanel.setStyle("""
            -fx-background-color: white;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);
            -fx-background-radius: 5;
            -fx-padding: 15;
            """);

        Label dadosTitulo = new Label("Dados Básicos");
        dadosTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox dadosFields = new HBox(15);
        dadosFields.getChildren().addAll(
                criarCampo("Número:", 100, "numeroCampo"),
                criarCampo("Nome:", 200, "nomeCampo"),
                criarCampo("Descrição:", 300, "descricaoCampo")
        );

        dadosPanel.getChildren().addAll(dadosTitulo, dadosFields);

        txtMensagens = new TextArea();
        txtMensagens.setStyle("-fx-font-family: 'Consolas';");
        txtMensagens.setPrefRowCount(5);
        txtMensagens.setEditable(false);

        Button btnVoltar = criarBotao("Voltar", e -> mostrarMenuPrincipal());
        Button btnCadastrar = criarBotao("Cadastrar", e -> cadastrarDrone());

        HBox botoesBox = new HBox(15, btnVoltar, btnCadastrar);
        botoesBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(titulo, dadosPanel, botoesBox, txtMensagens);

        Scene cadastroScene = new Scene(mainLayout, 700, 800);
        primaryStage.setTitle("Cadastrar Novo Transporte");
        primaryStage.setScene(cadastroScene);
    }

    private VBox criarCampo(String label, double width, String id) {
        VBox campo = new VBox(5);
        Label lblCampo = new Label(label);
        TextField txtCampo = new TextField();
        txtCampo.setId(id);
        txtCampo.setPrefWidth(width);
        campo.getChildren().addAll(lblCampo, txtCampo);
        return campo;
    }

    private Button criarBotao(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle(BUTTON_STYLE);
        btn.setOnMouseEntered(e -> btn.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btn.setOnMouseExited(e -> btn.setStyle(BUTTON_STYLE));
        btn.setOnAction(acao);
        return btn;
    }

    private void cadastrarDrone() {
        // Lógica de cadastro, pode ser adaptada para o gestor
        txtMensagens.appendText("Transporte cadastrado com sucesso!\n");
    }

    private void mostrarMenuPrincipal() {
        Menu menu = new Menu();
        menu.start(primaryStage);
    }
}