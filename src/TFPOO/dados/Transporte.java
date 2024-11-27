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

	public abstract double calcularCusto();

	//Getters e setters
	public int getNumero() { return numero; }
	public String getNomeCliente() { return nomeCliente; }
	public String getDescricao() { return descricao; }
	public double getLatitudeOrigem() { return latitudeOrigem; }
	public double getLongitudeOrigem() { return longitudeOrigem; }
	public double getLatitudeDestino() { return latitudeDestino; }
	public double getLongitudeDestino() { return longitudeDestino; }
	public Estado getSituacao() { return situacao; }
}