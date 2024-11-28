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
        Button btnProcessarPendentes = criarBotao("Transportes Pendentes", e -> mostrarTelaProcessarTransportesPendentes());
        Button btnRelatorio = criarBotao("Mostrar Relatório Geral", e -> mostrarRelatorioGeral());
        Button btnMostrarTransportes = criarBotao("Todos os Transportes", e -> mostrarTelaMostrarTodosOsTransportes());
        Button btnAlterarSituacao = criarBotao("Alterar Situação", e -> mostrarTelaAlterarASituacaoDeUmTransporte());
        Button btnLeituraSimulacao = criarBotao("Leitura de Simulação", e -> mostrarTelaRealizarLeituraSimulacao());
        Button btnSalvarDados = criarBotao("Salvar Dados", e -> System.out.println("Salvar Dados"));
        Button btnCarregarDados = criarBotao("Carregar Dados", e -> System.out.println("Carregar Dados"));
        Button btnFinalizar = criarBotao("Finalizar", e -> primaryStage.close());

        menuLayout.getChildren().addAll(
                btnCadastrarDrone,
                btnCadastrarTransporte,
                btnProcessarPendentes,
                btnRelatorio,
                btnMostrarTransportes,
                btnAlterarSituacao,
                btnLeituraSimulacao,
                btnSalvarDados,
                btnCarregarDados,
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

    private void mostrarTelaCadastroDrone() {
        CadastrarNovoDrone cd = new CadastrarNovoDrone();
        cd.setPrimaryStage(primaryStage);
        cd.mostrarTela();
    }

    private void mostrarTelaCadastroTransporte() {
        CadastrarNovoTransporte ct = new CadastrarNovoTransporte();
        ct.setPrimaryStage(primaryStage);
        ct.mostrarTela();
    }

    private void mostrarRelatorioGeral() {
        MostrarRelatorioGeral relatorio = new MostrarRelatorioGeral(primaryStage.getScene());
        relatorio.setPrimaryStage(primaryStage);
        relatorio.mostrarTela();
    }

    private void mostrarTelaMostrarTodosOsTransportes() {
        MostrarTodosOsTransportes mostrarTransportes = new MostrarTodosOsTransportes(primaryStage.getScene());
        mostrarTransportes.setPrimaryStage(primaryStage);
        mostrarTransportes.mostrarTela();
    }

    private void mostrarTelaProcessarTransportesPendentes() {
        ProcessarTransportesPendentes processarTransportes = new ProcessarTransportesPendentes(primaryStage.getScene());
        processarTransportes.setPrimaryStage(primaryStage);
        processarTransportes.mostrarTela();
    }

    public void mostrarTelaAlterarASituacaoDeUmTransporte() {
        AlterarASituacaoDeUmTransporte alterarASituacaoDeUmTransporte = new AlterarASituacaoDeUmTransporte(primaryStage.getScene());
        alterarASituacaoDeUmTransporte.setPrimaryStage(primaryStage);
        alterarASituacaoDeUmTransporte.mostrarTela();
    }

    public void mostrarTelaRealizarLeituraSimulacao() {
        RealizarLeituraDeDadosDeSimulacao rl = new RealizarLeituraDeDadosDeSimulacao();
        rl.setPrimaryStage(primaryStage);
        rl.mostrarTela();
    }

    public static void main(String[] args) {
        launch(args);
    }
}