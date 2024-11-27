package TFPOO.dados;

public class DroneCargaViva extends DroneCarga {

	//Atributos
	private boolean climatizado;

	//Construtor
	public DroneCargaViva(int codigo, double custoFixo, double autonomia, double pesoMaximo, boolean climatizado) {
		super(codigo, custoFixo, autonomia, pesoMaximo);
		this.climatizado = climatizado;
	}

	public double calculaCustoKm() {
		return 0;
	}
}