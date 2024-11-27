package TFPOO.interfaces;

import TFPOO.dados.*;
import TFPOO.gestores.DroneGestor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CadastrarNovoDrone {
    private Stage primaryStage;
    private DroneGestor droneGestor;
    private TabPane tabPane;

    private final String BUTTON_STYLE = """
            -fx-background-color: #1976D2FF;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 150px;
            """;
    private final String BUTTON_DISABLED_STYLE = """
            -fx-background-color: #A9A9A9;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-min-height: 40px;
            -fx-pref-width: 150px;
            """;
    private final String BUTTON_HOVER_STYLE = """
            -fx-background-color: rgba(25,118,210,0.75);
            -fx-cursor: hand;
            """;

    public void CadastroNovoDrone() {
        this.droneGestor = new DroneGestor();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        tabPane = new TabPane();

        Tab tabPessoal = criarAbaCadastroDronePessoal();
        Tab tabCargaInanimada = criarAbaCadastroDroneCargaInanimada();
        Tab tabCargaViva = criarAbaCadastroDroneCargaViva();

        tabPane.getTabs().addAll(tabPessoal, tabCargaInanimada, tabCargaViva);

        HBox botoesAcao = criarBotoesAcao();

        layout.getChildren().addAll(tabPane, botoesAcao);

        Scene scene = new Scene(layout, 500, 700);
        primaryStage.setTitle("Cadastrar Novo Drone");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab criarAbaCadastroDronePessoal() {
        Tab tabPessoal = new Tab("Drone Pessoal");
        tabPessoal.setClosable(false);
        VBox layoutPessoal = new VBox(20);
        layoutPessoal.setPadding(new Insets(15));

        TextField txtCodigo = criarCampoNumerico("Código do Drone");
        TextField txtCustoFixo = criarCampoNumericoDecimal("Custo Fixo");
        TextField txtAutonomia = criarCampoNumericoDecimal("Autonomia (km)");
        TextField txtQtdMaxPessoas = criarCampoNumerico("Quantidade Máxima de Pessoas");

        Button btnCadastrarPessoal = new Button("Cadastrar");
        btnCadastrarPessoal.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarPessoal.setDisable(true);

        adicionarValidacaoCompleta(btnCadastrarPessoal,
                txtCodigo, txtCustoFixo, txtAutonomia, txtQtdMaxPessoas);

        btnCadastrarPessoal.setOnAction(e -> {
            try {
                DronePessoal drone = new DronePessoal(
                        Integer.parseInt(txtCodigo.getText()),
                        Double.parseDouble(txtCustoFixo.getText()),
                        Double.parseDouble(txtAutonomia.getText()),
                        Integer.parseInt(txtQtdMaxPessoas.getText())
                );

                if (droneGestor.cadastrarDrone(drone)) {
                    mostrarAlerta("Sucesso", "Drone Pessoal cadastrado com sucesso!");
                    limparCampos(txtCodigo, txtCustoFixo, txtAutonomia, txtQtdMaxPessoas);
                } else {
                    mostrarAlerta("Erro", "Já existe um drone com este código!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutPessoal.getChildren().addAll(
                txtCodigo, txtCustoFixo, txtAutonomia, txtQtdMaxPessoas, btnCadastrarPessoal
        );

        tabPessoal.setContent(layoutPessoal);
        return tabPessoal;
    }

    private Tab criarAbaCadastroDroneCargaInanimada() {
        Tab tabCargaInanimada = new Tab("Drone Carga Inanimada");
        tabCargaInanimada.setClosable(false);
        VBox layoutCargaInanimada = new VBox(20);
        layoutCargaInanimada.setPadding(new Insets(15));

        TextField txtCodigo = criarCampoNumerico("Código do Drone");
        TextField txtCustoFixo = criarCampoNumericoDecimal("Custo Fixo");
        TextField txtAutonomia = criarCampoNumericoDecimal("Autonomia (km)");
        TextField txtPesoMaximo = criarCampoNumericoDecimal("Peso Máximo (kg)");
        CheckBox chkTemProtecao = new CheckBox("Possui Proteção");

        Button btnCadastrarInanimada = new Button("Cadastrar");
        btnCadastrarInanimada.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarInanimada.setDisable(true);

        adicionarValidacaoCompleta(btnCadastrarInanimada,
                txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo);

        btnCadastrarInanimada.setOnAction(e -> {
            try {
                DroneCargaInanimada drone = new DroneCargaInanimada(
                        Integer.parseInt(txtCodigo.getText()),
                        Double.parseDouble(txtCustoFixo.getText()),
                        Double.parseDouble(txtAutonomia.getText()),
                        Double.parseDouble(txtPesoMaximo.getText()),
                        chkTemProtecao.isSelected()
                );

                if (droneGestor.cadastrarDrone(drone)) {
                    mostrarAlerta("Sucesso", "Drone de Carga Inanimada cadastrado com sucesso!");
                    limparCampos(txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo);
                    chkTemProtecao.setSelected(false);
                } else {
                    mostrarAlerta("Erro", "Já existe um drone com este código!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutCargaInanimada.getChildren().addAll(
                txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo,
                chkTemProtecao, btnCadastrarInanimada
        );

        tabCargaInanimada.setContent(layoutCargaInanimada);
        return tabCargaInanimada;
    }

    private Tab criarAbaCadastroDroneCargaViva() {
        Tab tabCargaViva = new Tab("Drone Carga Viva");
        tabCargaViva.setClosable(false);
        VBox layoutCargaViva = new VBox(20);
        layoutCargaViva.setPadding(new Insets(15));
        tabPane.setTabMinWidth(130.1);

        TextField txtCodigo = criarCampoNumerico("Código do Drone");
        TextField txtCustoFixo = criarCampoNumericoDecimal("Custo Fixo");
        TextField txtAutonomia = criarCampoNumericoDecimal("Autonomia (km)");
        TextField txtPesoMaximo = criarCampoNumericoDecimal("Peso Máximo (kg)");
        CheckBox chkClimatizado = new CheckBox("É Climatizado");

        Button btnCadastrarViva = new Button("Cadastrar");
        btnCadastrarViva.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarViva.setDisable(true);

        adicionarValidacaoCompleta(btnCadastrarViva,
                txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo);

        btnCadastrarViva.setOnAction(e -> {
            try {
                DroneCargaViva drone = new DroneCargaViva(
                        Integer.parseInt(txtCodigo.getText()),
                        Double.parseDouble(txtCustoFixo.getText()),
                        Double.parseDouble(txtAutonomia.getText()),
                        Double.parseDouble(txtPesoMaximo.getText()),
                        chkClimatizado.isSelected()
                );

                if (droneGestor.cadastrarDrone(drone)) {
                    mostrarAlerta("Sucesso", "Drone de Carga Viva cadastrado com sucesso!");
                    limparCampos(txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo);
                    chkClimatizado.setSelected(false);
                } else {
                    mostrarAlerta("Erro", "Já existe um drone com este código!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutCargaViva.getChildren().addAll(
                txtCodigo, txtCustoFixo, txtAutonomia, txtPesoMaximo,
                chkClimatizado, btnCadastrarViva
        );

        tabCargaViva.setContent(layoutCargaViva);
        return tabCargaViva;
    }

    private HBox criarBotoesAcao() {
        HBox botoesAcao = new HBox(15);
        botoesAcao.setAlignment(Pos.CENTER);
        botoesAcao.setPadding(new Insets(10));

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle(BUTTON_STYLE);
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle(BUTTON_STYLE));
        btnVoltar.setOnAction(e -> {
            Menu menu = new Menu();
            menu.start(primaryStage);
        });

        botoesAcao.getChildren().add(btnVoltar);

        return botoesAcao;
    }

    private TextField criarCampoTexto(String texto) {
        TextField txtField = new TextField();
        txtField.setPromptText(texto);
        return txtField;
    }

    private TextField criarCampoNumerico(String texto) {
        TextField txtField = new TextField();
        txtField.setPromptText(texto);
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return txtField;
    }

    private TextField criarCampoNumericoDecimal(String texto) {
        TextField txtField = new TextField();
        txtField.setPromptText(texto);
        txtField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
        return txtField;
    }

    private void adicionarValidacaoCompleta(Button botaoCadastrar, TextField... campos) {
        for (TextField campo : campos) {
            campo.textProperty().addListener((observable, oldValue, newValue) -> {
                verificarCamposPreenchidos(botaoCadastrar, campos);
            });
        }
    }

    private void verificarCamposPreenchidos(Button botaoCadastrar, TextField... campos) {
        boolean todosCamposPreenchidos = true;

        for (TextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                todosCamposPreenchidos = false;
                break;
            }
        }

        if (todosCamposPreenchidos) {
            botaoCadastrar.setStyle(BUTTON_STYLE);
            botaoCadastrar.setOnMouseEntered(e -> botaoCadastrar.setStyle(BUTTON_STYLE + BUTTON_HOVER_STYLE));
            botaoCadastrar.setOnMouseExited(e -> botaoCadastrar.setStyle(BUTTON_STYLE));
            botaoCadastrar.setDisable(false);
        } else {
            botaoCadastrar.setStyle(BUTTON_DISABLED_STYLE);
            botaoCadastrar.setDisable(true);
        }
    }

    private void limparCampos(TextField... campos) {
        for (TextField campo : campos) {
            campo.clear();
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