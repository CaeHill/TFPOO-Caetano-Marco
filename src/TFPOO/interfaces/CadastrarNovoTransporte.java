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

public class CadastrarNovoTransporte {
    private Stage primaryStage;
    private TransporteGestor transporteGestor = SistemaGestores.getTransporteGestor();
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

    public CadastrarNovoTransporte() {}

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void mostrarTela() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Criar TabPane para diferentes tipos de transporte
        tabPane = new TabPane();

        // Adicionar abas para cada tipo de transporte
        Tab tabPessoal = criarAbaCadastroTransportePessoal();
        Tab tabCargaInanimada = criarAbaCadastroTransporteCargaInanimada();
        Tab tabCargaViva = criarAbaCadastroTransporteCargaViva();

        tabPane.getTabs().addAll(tabPessoal, tabCargaInanimada, tabCargaViva);

        // Botões de ação
        HBox botoesAcao = criarBotoesAcao();

        layout.getChildren().addAll(tabPane, botoesAcao);

        Scene scene = new Scene(layout, 500, 700);
        primaryStage.setTitle("Cadastrar Novo Transporte");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab criarAbaCadastroTransportePessoal() {
        Tab tabPessoal = new Tab("Transporte Pessoal");
        tabPessoal.setClosable(false);
        VBox layoutPessoal = new VBox(20);
        layoutPessoal.setPadding(new Insets(15));

        // Campos de texto para Transporte Pessoal
        TextField txtNumero = criarCampoNumerico("Número do Transporte");
        TextField txtNomeCliente = criarCampoTexto("Nome do Cliente");
        TextField txtDescricao = criarCampoTexto("Descrição");
        TextField txtPeso = criarCampoNumericoDecimal("Peso");
        TextField txtLatOrigem = criarCampoNumericoDecimal("Latitude de Origem");
        TextField txtLatDestino = criarCampoNumericoDecimal("Latitude de Destino");
        TextField txtLongOrigem = criarCampoNumericoDecimal("Longitude de Origem");
        TextField txtLongDestino = criarCampoNumericoDecimal("Longitude de Destino");
        TextField txtQtdPassageiros = criarCampoNumerico("Quantidade de Passageiros");

        Button btnCadastrarPessoal = new Button("Cadastrar");
        btnCadastrarPessoal.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarPessoal.setDisable(true);

        // Adicionar listeners para habilitar/desabilitar botão
        adicionarValidacaoCompleta(btnCadastrarPessoal,
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino,
                txtQtdPassageiros);

        btnCadastrarPessoal.setOnAction(e -> {
            try {
                TransportePessoal transporte = new TransportePessoal(
                        Integer.parseInt(txtNumero.getText()),
                        txtNomeCliente.getText(),
                        txtDescricao.getText(),
                        Double.parseDouble(txtPeso.getText()),
                        Double.parseDouble(txtLatOrigem.getText()),
                        Double.parseDouble(txtLatDestino.getText()),
                        Double.parseDouble(txtLongOrigem.getText()),
                        Double.parseDouble(txtLongDestino.getText()),
                        Integer.parseInt(txtQtdPassageiros.getText())
                );

                if (transporteGestor.cadastrarTransporte(transporte)) {
                    transporteGestor.adicionarTransportePendentes(transporte);
                    mostrarAlerta("Sucesso", "Transporte Pessoal cadastrado com sucesso!");
                    limparCampos(txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                            txtLatOrigem, txtLatDestino, txtLongOrigem,
                            txtLongDestino, txtQtdPassageiros);
                } else {
                    mostrarAlerta("Erro", "Já existe um transporte com este número!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutPessoal.getChildren().addAll(
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino,
                txtQtdPassageiros, btnCadastrarPessoal
        );

        tabPessoal.setContent(layoutPessoal);
        return tabPessoal;
    }

    private Tab criarAbaCadastroTransporteCargaInanimada() {
        Tab tabCargaInanimada = new Tab("Transporte Carga Inanimada");
        tabCargaInanimada.setClosable(false);
        VBox layoutCargaInanimada = new VBox(20);
        layoutCargaInanimada.setPadding(new Insets(15));

        // Campos de texto para Carga Inanimada
        TextField txtNumero = criarCampoNumerico("Número do Transporte");
        TextField txtNomeCliente = criarCampoTexto("Nome do Cliente");
        TextField txtDescricao = criarCampoTexto("Descrição");
        TextField txtPeso = criarCampoNumericoDecimal("Peso");
        TextField txtLatOrigem = criarCampoNumericoDecimal("Latitude de Origem");
        TextField txtLatDestino = criarCampoNumericoDecimal("Latitude de Destino");
        TextField txtLongOrigem = criarCampoNumericoDecimal("Longitude de Origem");
        TextField txtLongDestino = criarCampoNumericoDecimal("Longitude de Destino");

        CheckBox chkCargaPerigosa = new CheckBox("Carga Perigosa");

        Button btnCadastrarInanimada = new Button("Cadastrar");
        btnCadastrarInanimada.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarInanimada.setDisable(true);

        // Adicionar listeners para habilitar/desabilitar botão
        adicionarValidacaoCompleta(btnCadastrarInanimada,
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino);

        btnCadastrarInanimada.setOnAction(e -> {
            try {
                TransporteCargaInanimada transporte = new TransporteCargaInanimada(
                        Integer.parseInt(txtNumero.getText()),
                        txtNomeCliente.getText(),
                        txtDescricao.getText(),
                        Double.parseDouble(txtPeso.getText()),
                        Double.parseDouble(txtLatOrigem.getText()),
                        Double.parseDouble(txtLatDestino.getText()),
                        Double.parseDouble(txtLongOrigem.getText()),
                        Double.parseDouble(txtLongDestino.getText()),
                        chkCargaPerigosa.isSelected()
                );

                if (transporteGestor.cadastrarTransporte(transporte)) {
                    transporteGestor.adicionarTransportePendentes(transporte);
                    mostrarAlerta("Sucesso", "Transporte de Carga Inanimada cadastrado com sucesso!");
                    limparCampos(txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                            txtLatOrigem, txtLatDestino, txtLongOrigem,
                            txtLongDestino);
                    chkCargaPerigosa.setSelected(false);
                } else {
                    mostrarAlerta("Erro", "Já existe um transporte com este número!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutCargaInanimada.getChildren().addAll(
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino,
                chkCargaPerigosa, btnCadastrarInanimada
        );

        tabCargaInanimada.setContent(layoutCargaInanimada);
        return tabCargaInanimada;
    }

    private Tab criarAbaCadastroTransporteCargaViva() {
        Tab tabCargaViva = new Tab("Transporte Carga Viva");
        tabCargaViva.setClosable(false);
        VBox layoutCargaViva = new VBox(20);
        layoutCargaViva.setPadding(new Insets(15));
        tabPane.setTabMinWidth(121.1);

        // Campos de texto para Carga Viva
        TextField txtNumero = criarCampoNumerico("Número do Transporte");
        TextField txtNomeCliente = criarCampoTexto("Nome do Cliente");
        TextField txtDescricao = criarCampoTexto("Descrição");
        TextField txtPeso = criarCampoNumericoDecimal("Peso");
        TextField txtLatOrigem = criarCampoNumericoDecimal("Latitude de Origem");
        TextField txtLatDestino = criarCampoNumericoDecimal("Latitude de Destino");
        TextField txtLongOrigem = criarCampoNumericoDecimal("Longitude de Origem");
        TextField txtLongDestino = criarCampoNumericoDecimal("Longitude de Destino");
        TextField txtTemperaturaMin = criarCampoNumericoDecimal("Temperatura Mínima");
        TextField txtTemperaturaMax = criarCampoNumericoDecimal("Temperatura Máxima");

        Button btnCadastrarViva = new Button("Cadastrar");
        btnCadastrarViva.setStyle(BUTTON_DISABLED_STYLE);
        btnCadastrarViva.setDisable(true);

        // Adicionar listeners para habilitar/desabilitar botão
        adicionarValidacaoCompleta(btnCadastrarViva,
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino,
                txtTemperaturaMin, txtTemperaturaMax);

        btnCadastrarViva.setOnAction(e -> {
            try {
                TransporteCargaViva transporte = new TransporteCargaViva(
                        Integer.parseInt(txtNumero.getText()),
                        txtNomeCliente.getText(),
                        txtDescricao.getText(),
                        Double.parseDouble(txtPeso.getText()),
                        Double.parseDouble(txtLatOrigem.getText()),
                        Double.parseDouble(txtLatDestino.getText()),
                        Double.parseDouble(txtLongOrigem.getText()),
                        Double.parseDouble(txtLongDestino.getText()),
                        Double.parseDouble(txtTemperaturaMin.getText()),
                        Double.parseDouble(txtTemperaturaMax.getText())
                );

                if (transporteGestor.cadastrarTransporte(transporte)) {
                    transporteGestor.adicionarTransportePendentes(transporte);
                    mostrarAlerta("Sucesso", "Transporte de Carga Viva cadastrado com sucesso!");
                    limparCampos(txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                            txtLatOrigem, txtLatDestino, txtLongOrigem,
                            txtLongDestino, txtTemperaturaMin, txtTemperaturaMax);
                } else {
                    mostrarAlerta("Erro", "Já existe um transporte com este número!");
                }
            } catch (NumberFormatException ex) {
                mostrarAlerta("Erro", "Por favor, verifique os valores numéricos inseridos.");
            }
        });

        layoutCargaViva.getChildren().addAll(
                txtNumero, txtNomeCliente, txtDescricao, txtPeso,
                txtLatOrigem, txtLatDestino, txtLongOrigem, txtLongDestino,
                txtTemperaturaMin, txtTemperaturaMax, btnCadastrarViva
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
            // Permitir apenas números inteiros
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
            // Permitir apenas números e um ponto decimal
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtField.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
        return txtField;
    }

    private void adicionarValidacaoCompleta(Button botaoCadastrar, TextField... campos) {
        // Adiciona listeners para todos os campos para verificar quando todos estão preenchidos
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

        // Atualizar estilo e estado do botão
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