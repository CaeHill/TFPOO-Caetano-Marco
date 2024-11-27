package TFPOO.dados;

public class TransportePessoal extends Transporte {

	//Atributos
	private int qtdPassageiros;

	//Construtor
	public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, int qtdPassageiros) {
		super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
		this.qtdPassageiros = qtdPassageiros;
	}

	@Override
	public double calcularCusto() { return 0; }
}