package TFPOO.dados;

public class TransporteCargaInanimada extends Transporte {

	//Atributos
	private boolean cargaPerigosa;

	//Construtor
	public TransporteCargaInanimada(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, boolean cargaPerigosa) {
		super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
		this.cargaPerigosa = cargaPerigosa;
	}

	@Override
	public double calcularCusto() {
		if (getDrone() == null) {
			throw new IllegalStateException("Drone não alocado! Não é possível calcular o custo.");
		}

		double custoPorKm = getDrone().calculaCustoKm();
		double distancia = calcularDistancia();
		double custoTransporte = custoPorKm * distancia;

		double acrescimo = cargaPerigosa ? 500.00 : 0.00;
		return custoTransporte + acrescimo;
	}

	//Getters
	public boolean getCargaPerigosa() { return cargaPerigosa; }
}