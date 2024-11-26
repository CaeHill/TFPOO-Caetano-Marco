package projeto.dados;

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
	private String situacao; // PENDENTE, ALOCADO, TERMINADO, CANCELADO
	private double custoFinal;

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
	}

	public abstract void calcularCusto();

	// Getters e setters
	public int getNumero() { return numero; }
	public String getNomeCliente() { return nomeCliente; }
	public String getDescricao() { return descricao; }
	public double getLatitudeOrigem() { return latitudeOrigem; }
	public double getLongitudeOrigem() { return longitudeOrigem; }
	public double getLatitudeDestino() { return latitudeDestino; }
	public double getLongitudeDestino() { return longitudeDestino; }
	public double getCustoFinal() { return custoFinal; }
	public void setCustoFinal(double custoFinal) { this.custoFinal = custoFinal; }
}
