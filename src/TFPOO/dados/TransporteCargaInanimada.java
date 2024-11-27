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
	public double calcularCusto() { return 0; }
}