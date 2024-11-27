package TFPOO.dados;

public class DroneCargaInanimada extends DroneCarga {

	//Atributos
	private boolean protecao;

	//Construtor
	public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.protecao = protecao;
	}

	public double calculaCustoKm() {
		return 0;
	}
}