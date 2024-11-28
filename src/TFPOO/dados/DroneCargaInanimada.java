package TFPOO.dados;

public class DroneCargaInanimada extends DroneCarga {

	//Atributos
	private boolean protecao;

	//Construtor
	public DroneCargaInanimada(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean protecao) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.protecao = protecao;
	}

	@Override
	public double calculaCustoKm() {
		double custoVariado = protecao ? 10.00 : 5.00;
		return getCustoFixo() + custoVariado;
	}

	//Getters
	public boolean isProtecao() { return protecao; }
}