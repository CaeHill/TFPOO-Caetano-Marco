package TFPOO.dados;

public abstract class Transporte {

	//Atributos
	private int numero;
	private String nomeCliente;
	private String descricao;
	private double peso;
	private double latitudeOrigem;
	private double latitudeDestino;
	private double longitudeOrigem;
	private double longitudeDestino;
	private Estado situacao;
	private Drone drone;

	//Construtor
	public Transporte(int numero, String nomeCliente, String descricao, double peso, double latitudeOrigem, double latitudeDestino, double longitudeOrigem, double longitudeDestino) {
		this.numero = numero;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.peso = peso;
		this.latitudeOrigem = latitudeOrigem;
		this.latitudeDestino = latitudeDestino;
		this.longitudeOrigem = longitudeOrigem;
		this.longitudeDestino = longitudeDestino;
		this.situacao = Estado.PENDENTE;
	}

	public double calcularDistancia() {
		final double R = 6371.0;
		double lat1 = Math.toRadians(getLatitudeOrigem());
		double lon1 = Math.toRadians(getLongitudeOrigem());
		double lat2 = Math.toRadians(getLatitudeDestino());
		double lon2 = Math.toRadians(getLongitudeDestino());

		double dlat = lat2 - lat1;
		double dlon = lon2 - lon1;

		double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
				Math.cos(lat1) * Math.cos(lat2) *
						Math.sin(dlon / 2) * Math.sin(dlon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	public void alocarDrone(Drone drone) {
		this.drone = drone;
		this.situacao = Estado.ALOCADO;
	}

	public abstract double calcularCusto();

	//Getters
	public int getNumero() { return numero; }
	public String getNomeCliente() { return nomeCliente; }
	public String getDescricao() { return descricao; }
	public double getPeso() { return peso; }
	public double getLatitudeOrigem() { return latitudeOrigem; }
	public double getLongitudeOrigem() { return longitudeOrigem; }
	public double getLatitudeDestino() { return latitudeDestino; }
	public double getLongitudeDestino() { return longitudeDestino; }
	public Estado getSituacao() { return situacao; }
	public Drone getDrone() { return drone; }

	//Setters
	public void setSituacao(Estado situacao) { this.situacao = situacao; }
	public void setDrone (Drone drone) { this.drone = drone; }
}