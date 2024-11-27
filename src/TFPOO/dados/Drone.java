package TFPOO.dados;

public abstract class Drone {

	//Atributos
	private int codigo;
	private double custoFixo;
	private double autonomia;

	//Construtor
	public Drone(int codigo, double custoFixo, double autonomia) {
		this.codigo = codigo;
		this.custoFixo = custoFixo;
		this.autonomia = autonomia;
	}

	public abstract double calculaCustoKm();

	//Getters
	public int getCodigo() { return codigo; }
	public double getCustoFixo() { return custoFixo; }
	public double getAutonomia() { return autonomia; }
}