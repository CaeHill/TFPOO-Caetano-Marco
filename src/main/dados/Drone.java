package main.dados;

public abstract class Drone {

	private int codigo;

	private double custoFixo;

	private double autonomia;

	public abstract double calculaCustoKm();

}