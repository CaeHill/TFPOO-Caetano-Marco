package main.dados;

public class TransportePessoal extends Transporte {
	private int numero;
	private String cliente;
	private String descricao;
	private int qtdPassageiros;
	private int tempMin;
	private int tempMax;
	private double valorPassagem;
	private int distanciaPercorrida;
	private double valorTotal;
	private int tipoTransporte;

	public TransportePessoal(int numero, String nomeCliente, String descricao, double peso,
							 double latOrig, double longOrig, double latDest, double longDest,
							 int qtdPassageiros, int tempMin, int tempMax) {
		super(numero, nomeCliente, descricao, peso, latOrig, longOrig, latDest, longDest);
		this.qtdPassageiros = qtdPassageiros;
		this.tempMin = tempMin;
		this.tempMax = tempMax;
	}

	public TransportePessoal(int numero, String cliente, String descricao, int peso, double latOrig, double longOrig, double latDest, double longDest, int passageiros, int tempMin, int tempMax, int i, int i1, int i2, int i3, int i4) {
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
		double distancia = Math.sqrt(Math.pow(getLatDest() - getLatOrig(), 2) +
				Math.pow(getLongDest() - getLongOrig(), 2));
		double custo = distancia * 10 + qtdPassageiros * 10;
		setCustoFinal(custo);
	}
}