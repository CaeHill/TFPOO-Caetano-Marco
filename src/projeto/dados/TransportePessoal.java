package projeto.dados;

public class TransportePessoal extends Transporte {

	//Atributos
	private int qtdPassageiros;

	//Construtor
	public TransportePessoal(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, int qtdPassageiros) {
		super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
		this.qtdPassageiros = qtdPassageiros;
	}

	@Override
	public String toString() {
		return "TransportePessoas{" +
				"numero=" + getNumero() +
				", cliente='" + getNomeCliente() + '\'' +
				", descricao='" + getDescricao() + '\'' +
				", passageiros=" + qtdPassageiros +
				", custoFinal=" + getCustoFinal() +
				'}';
	}

	@Override
	public void calcularCusto() {
		double distancia = Math.sqrt(Math.pow(getLatitudeDestino() - getLatitudeOrigem(), 2) +
				Math.pow(getLongitudeDestino() - getLongitudeOrigem(), 2));
		double custo = distancia * 10 + qtdPassageiros * 10;
		setCustoFinal(custo);
	}
}