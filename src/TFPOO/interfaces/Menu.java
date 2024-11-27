package TFPOO.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Application {

    private Stage primaryStage;
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

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        VBox menuLayout = new VBox(15);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setAlignment(Pos.CENTER);

        Button btnCadastrarDrone = criarBotao("Cadastrar Novo Drone", e -> mostrarTelaCadastroDrone());
        Button btnCadastrarTransporte = criarBotao("Cadastrar Novo Transporte", e -> mostrarTelaCadastroTransporte());
        Button btnCarregarDados = criarBotao("Carregar Dados", e -> System.out.println("Carregar Dados"));
        Button btnAlterarSituacao = criarBotao("Alterar Situação", e -> System.out.println("Alterar Situação"));
        Button btnRelatorio = criarBotao("Mostrar Relatório Geral", e -> System.out.println("Mostrar Relatório Geral"));
        Button btnMostrarTransportes = criarBotao("Mostrar Todos os Transportes", e -> System.out.println("Mostrar Transportes"));
        Button btnProcessarPendentes = criarBotao("Processar Transportes Pendentes", e -> System.out.println("Processar Pendentes"));
        Button btnLeituraSimulacao = criarBotao("Realizar Leitura de Simulação", e -> System.out.println("Leitura de Simulação"));
        Button btnSalvarDados = criarBotao("Salvar Dados", e -> System.out.println("Salvar Dados"));
        Button btnFinalizar = criarBotao("Finalizar", e -> primaryStage.close());

        menuLayout.getChildren().addAll(
                btnCadastrarDrone,
                btnCadastrarTransporte,
                btnCarregarDados,
                btnAlterarSituacao,
                btnRelatorio,
                btnMostrarTransportes,
                btnProcessarPendentes,
                btnLeituraSimulacao,
                btnSalvarDados,
                btnFinalizar
        );

        Scene menuScene = new Scene(menuLayout, 400, 600);
        primaryStage.setTitle("Menu Principal");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private Button criarBotao(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle(BUTTON_STYLE);
        btn.setOnMouseEntered(e -> btn.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btn.setOnMouseExited(e -> btn.setStyle(BUTTON_STYLE));
        btn.setOnAction(acao);
        return btn;
    }

    private void mostrarTelaCadastroTransporte() {
        CadastrarNovoTransporte ct = new CadastrarNovoTransporte();
        ct.setPrimaryStage(primaryStage);
        ct.mostrarTela();
    }

    private void mostrarTelaCadastroDrone() {
        CadastrarNovoDrone cd = new CadastrarNovoDrone();
        cd.setPrimaryStage(primaryStage);
        cd.mostrarTela();
    }

    public static void main(String[] args) {
        launch(args);
    }
}