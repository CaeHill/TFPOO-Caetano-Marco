package projeto.interfaces;

import projeto.dados.*;
import projeto.gestores.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CadastrarNovoTransporte extends Application {
    private TextArea txtMensagens;
    private TransporteGestor gestor;

    private final String BUTTON_STYLE = """
            -fx-background-color: #2196F3;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-width: 120px;
            -fx-min-height: 35px;
            """;

    private final String BUTTON_HOVER_STYLE = """
            -fx-background-color: #1976D2;
            -fx-cursor: hand;
            """;

    @Override
    public void start(Stage stage) {
        gestor = new TransporteGestor();

        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        Label titulo = new Label("Cadastro de Transporte");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        VBox dadosBasicosPanel = new VBox(15);
        dadosBasicosPanel.setStyle("""
            -fx-background-color: white;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);
            -fx-background-radius: 5;
            -fx-padding: 15;
            """);

        Label dadosBasicosTitulo = new Label("Dados Básicos");
        dadosBasicosTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox dadosBasicosFields = new HBox(15);
        dadosBasicosFields.getChildren().addAll(
                criarCampo("Número:", 100, "numeroCampo"),
                criarCampo("Nome:", 200, "nomeCampo"),
                criarCampo("Descrição:", 300, "descricaoCampo")
        );

        dadosBasicosPanel.getChildren().addAll(dadosBasicosTitulo, dadosBasicosFields);

        VBox localizacaoPanel = new VBox(15);
        localizacaoPanel.setStyle("""
            -fx-background-color: white;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);
            -fx-background-radius: 5;
            -fx-padding: 15;
            """);

        Label localizacaoTitulo = new Label("Localização");
        localizacaoTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox origemFields = new HBox(15);
        origemFields.getChildren().addAll(
                criarCampo("Latitude de Origem:", 150, "latOrigCampo"),
                criarCampo("Longitude de Origem:", 150, "longOrigCampo")
        );

        HBox destinoFields = new HBox(15);
        destinoFields.getChildren().addAll(
                criarCampo("Latitude de Destino:", 150, "latDestCampo"),
                criarCampo("Longitude de Destino:", 150, "longDestCampo")
        );

        localizacaoPanel.getChildren().addAll(localizacaoTitulo, origemFields, destinoFields);

        VBox especificacoesPanel = new VBox(15);
        especificacoesPanel.setStyle("""
            -fx-background-color: white;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);
            -fx-background-radius: 5;
            -fx-padding: 15;
            """);

        Label especificacoesTitulo = new Label("Especificações");
        especificacoesTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        GridPane especificacoesGrid = new GridPane();
        especificacoesGrid.setHgap(15);
        especificacoesGrid.setVgap(10);

        especificacoesGrid.add(new Label("Peso:"), 0, 0);
        especificacoesGrid.add(new TextField() {{
            setId("pesoCampo");
        }}, 1, 0);
        especificacoesGrid.add(new Label("Número de Passageiros:"), 2, 0);
        especificacoesGrid.add(new TextField() {{
            setId("passageirosCampo");
        }}, 3, 0);
        especificacoesGrid.add(new Label("Temp. Mínima:"), 0, 1);
        especificacoesGrid.add(new TextField() {{
            setId("tempMinCampo");
        }}, 1, 1);
        especificacoesGrid.add(new Label("Temp. Máxima:"), 2, 1);
        especificacoesGrid.add(new TextField() {{
            setId("tempMaxCampo");
        }}, 3, 1);

        especificacoesPanel.getChildren().addAll(especificacoesTitulo, especificacoesGrid);

        txtMensagens = new TextArea();
        txtMensagens.setStyle("-fx-font-family: 'Consolas';");
        txtMensagens.setPrefRowCount(5);
        txtMensagens.setEditable(false);

        TitledPane areaMensagens = new TitledPane("Mensagens do Sistema", txtMensagens);
        areaMensagens.setCollapsible(false);

        HBox botoesBox = criarBarraBotoes();
        botoesBox.setAlignment(Pos.CENTER);

        mainLayout.getChildren().addAll(
                titulo,
                dadosBasicosPanel,
                localizacaoPanel,
                especificacoesPanel,
                botoesBox,
                areaMensagens
        );

        Scene scene = new Scene(mainLayout);
        stage.setTitle("Sistema de Transportes");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setHeight(800);
        stage.show();
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


    private HBox criarBarraBotoes() {
        Button btnCadastrar = criarBotao("Cadastrar");
        Button btnLimpar = criarBotao("Limpar");
        Button btnListar = criarBotao("Listar Transportes");
        Button btnSair = criarBotao("Sair");

        btnCadastrar.setOnAction(e -> cadastrarTransporte());
        btnLimpar.setOnAction(e -> limparCampos());
        btnListar.setOnAction(e -> listarTransportes());
        btnSair.setOnAction(e -> ((Stage) btnSair.getScene().getWindow()).close());

        HBox botoesBox = new HBox(15, btnCadastrar, btnLimpar, btnListar, btnSair);
        botoesBox.setAlignment(Pos.CENTER);
        botoesBox.setPadding(new Insets(7, 0, 7, 0));

        return botoesBox;
    }

    private Button criarBotao(String texto) {
        Button btn = new Button(texto);
        btn.setStyle(BUTTON_STYLE);
        btn.setOnMouseEntered(e -> btn.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btn.setOnMouseExited(e -> btn.setStyle(BUTTON_STYLE));
        return btn;
    }

    private void cadastrarTransporte() {
        try {
            Scene scene = txtMensagens.getScene();
            int numero = Integer.parseInt(((TextField) scene.lookup("#numeroCampo")).getText());
            String nome = ((TextField) scene.lookup("#nomeCampo")).getText();
            String descricao = ((TextField) scene.lookup("#descricaoCampo")).getText();
            double peso = Double.parseDouble(((TextField) scene.lookup("#pesoCampo")).getText());
            double latOrig = Double.parseDouble(((TextField) scene.lookup("#latOrigCampo")).getText());
            double longOrig = Double.parseDouble(((TextField) scene.lookup("#longOrigCampo")).getText());
            double latDest = Double.parseDouble(((TextField) scene.lookup("#latDestCampo")).getText());
            double longDest = Double.parseDouble(((TextField) scene.lookup("#longDestCampo")).getText());
            int passageiros = Integer.parseInt(((TextField) scene.lookup("#passageirosCampo")).getText());
            int tempMin = Integer.parseInt(((TextField) scene.lookup("#tempMinCampo")).getText());
            int tempMax = Integer.parseInt(((TextField) scene.lookup("#tempMaxCampo")).getText());

            TransportePessoal transporte = new TransportePessoal(numero, nome, descricao, peso, latOrig, longOrig, latDest, longDest, passageiros, tempMin, tempMax);

            if (gestor.cadastrarTransporte(transporte)) {
                txtMensagens.appendText("Transporte cadastrado com sucesso!\n");
            } else {
                txtMensagens.appendText("Erro: Transporte com o mesmo número já existe.\n");
            }
        } catch (NumberFormatException ex) {
            txtMensagens.appendText("Erro: Preencha todos os campos corretamente.\n");
        }
    }


    private void limparCampos() {
        Scene scene = txtMensagens.getScene();
        scene.getRoot().lookupAll(".text-field")
                .forEach(node -> ((TextField) node).clear());
        txtMensagens.clear();
    }

    private void listarTransportes() {
        txtMensagens.appendText(gestor.listarTodosTransportes() + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}