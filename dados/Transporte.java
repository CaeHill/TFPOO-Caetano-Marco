package dados;

public abstract class Transporte {
	private int numero;
	private String nomeCliente;
	private String descricao;
	private double peso;
	private double latOrig;
	private double longOrig;
	private double latDest;
	private double longDest;
	private String status; // PENDENTE, ALOCADO, TERMINADO, CANCELADO
	private double custoFinal;

	public Transporte(int numero, String nomeCliente, String descricao, double peso,
					  double latOrig, double longOrig, double latDest, double longDest) {
		this.numero = numero;
		this.nomeCliente = nomeCliente;
		this.descricao = descricao;
		this.peso = peso;
		this.latOrig = latOrig;
		this.longOrig = longOrig;
		this.latDest = latDest;
		this.longDest = longDest;
	}

	public Transporte() {

	}


	public abstract void calcularCusto();

	public void alterarStatus(String novoStatus) {
		if (!status.equals("TERMINADO") && !status.equals("CANCELADO")) {
			this.status = novoStatus;
		}
	}

	// Getters e setters

	public int getNumero() { return numero; }
	public String getNomeCliente() { return nomeCliente; }
	public String getDescricao() { return descricao; }
	public double getLatOrig() { return latOrig; }
	public double getLongOrig() { return longOrig; }
	public double getLatDest() { return latDest; }
	public double getLongDest() { return longDest; }
	public double getCustoFinal() { return custoFinal; }
	public void setCustoFinal(double custoFinal) { this.custoFinal = custoFinal; }
}
