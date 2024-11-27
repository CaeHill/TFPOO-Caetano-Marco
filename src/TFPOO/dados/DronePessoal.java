package TFPOO.dados;

public class DronePessoal extends Drone {

	//Atributos
	private int qtdMaxPessoas;

	//Construtor
	public DronePessoal(int codigo, double custoFixo, double autonomia, int qtdMaxPessoas) {
		super(codigo, custoFixo, autonomia);
		this.qtdMaxPessoas = qtdMaxPessoas;
	}

	@Override
	public double calculaCustoKm() {
		double custoVariado = 2.00 * qtdMaxPessoas;
		return getCustoFixo() + custoVariado;
	}
}