package TFPOO.dados;

public abstract class DroneCarga extends Drone {

	//Atributos
	private double pesoMaximo;

	//Construtor
	public DroneCarga(int codigo, double custoFixo, double autonomia, double pesoMaximo) {
		super(codigo, custoFixo, autonomia);
		this.pesoMaximo = pesoMaximo;
	}

	//Getters
	public double getPesoMaximo() { return pesoMaximo; }
}