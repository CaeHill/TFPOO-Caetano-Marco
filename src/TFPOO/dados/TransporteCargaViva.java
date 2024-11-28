package TFPOO.dados;

public class TransporteCargaViva extends Transporte {

	//Atributos
	private double temperaturaMinima;
	private double temperaturaMaxima;

	//Construtor
	public TransporteCargaViva(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino, double temperaturaMinima, double temperaturaMaxima) {
		super(numero, nomeCliente, descricao, peso, latitudeOrigem, latitudeDestino, longitudeOrigem, longitudeDestino);
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
	}

	@Override
	public double calcularCusto() {
		if (getDrone() == null) {
			throw new IllegalStateException("Drone não alocado! Não é possível calcular o custo.");
		}

		double custoPorKm = getDrone().calculaCustoKm();
		double distancia = calcularDistancia();
		double custoTransporte = custoPorKm * distancia;

		double acrescimo = (temperaturaMaxima - temperaturaMinima) > 10.00 ? 1000.00 : 0.00;
		return custoTransporte + acrescimo;
	}

	//Getters
	public double getTemperaturaMinima() { return temperaturaMinima; }
	public double getTemperaturaMaxima() { return temperaturaMaxima; }
}