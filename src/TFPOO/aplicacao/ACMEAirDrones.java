package TFPOO.aplicacao;

import TFPOO.dados.*;
import TFPOO.gestores.*;
import TFPOO.interfaces.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ACMEAirDrones {
	DroneGestor dg = new DroneGestor();
	TransporteGestor tg = new TransporteGestor();

	public void executar() {
		leitorSIMULADRONESCSV();
		leitorSIMULATRASPORTESCSV();
	}

	public void leitorSIMULADRONESCSV() {
		try (BufferedReader br = new BufferedReader(new FileReader("SIMULA-DRONES.CSV"))) {
			String linha;

			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(";");

				if (dados[0].equals("1")) {
					DronePessoal dp = new DronePessoal(Integer.parseInt(dados[1]),Double.parseDouble(dados[2]),Double.parseDouble(dados[3]),Integer.parseInt(dados[4]));
					dg.cadastrarDrone(dp);
				} else if (dados[0].equals("2")) {
					DroneCargaInanimada dci = new DroneCargaInanimada(Integer.parseInt(dados[1]),Double.parseDouble(dados[2]),Double.parseDouble(dados[3]),Double.parseDouble(dados[4]),Boolean.parseBoolean(dados[5]));
					dg.cadastrarDrone(dci);
				} else if (dados[0].equals("3")) {
					DroneCargaViva dcv = new DroneCargaViva(Integer.parseInt(dados[1]),Double.parseDouble(dados[2]),Double.parseDouble(dados[3]),Double.parseDouble(dados[4]),Boolean.parseBoolean(dados[5]));
					dg.cadastrarDrone(dcv);
				}

				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
	}

	public void leitorSIMULATRASPORTESCSV() {
		try (BufferedReader br = new BufferedReader(new FileReader("SIMULA-TRANSPORTES.CSV"))) {
			String linha;

			while ((linha = br.readLine()) != null) {
				String[] dados = linha.split(";");

				if (dados[0].equals("1")) {
					TransportePessoal tp = new TransportePessoal(Integer.parseInt(dados[1]),dados[2],dados[3],Double.parseDouble(dados[4]),Double.parseDouble(dados[5]),Double.parseDouble(dados[6]),Double.parseDouble(dados[7]),Double.parseDouble(dados[8]),Integer.parseInt(dados[9]));
					tg.cadastrarTransporte(tp);
				} else if (dados[0].equals("2")) {
					TransporteCargaInanimada tci = new TransporteCargaInanimada(Integer.parseInt(dados[1]),dados[2],dados[3],Double.parseDouble(dados[4]),Double.parseDouble(dados[5]),Double.parseDouble(dados[6]),Double.parseDouble(dados[7]),Double.parseDouble(dados[8]),Boolean.parseBoolean(dados[9]));
					tg.cadastrarTransporte(tci);
				} else if (dados[0].equals("3")) {
					TransporteCargaViva tcv = new TransporteCargaViva(Integer.parseInt(dados[1]),dados[2],dados[3],Double.parseDouble(dados[4]),Double.parseDouble(dados[5]),Double.parseDouble(dados[6]),Double.parseDouble(dados[7]),Double.parseDouble(dados[8]),Double.parseDouble(dados[9]),Double.parseDouble(dados[10]));
					tg.cadastrarTransporte(tcv);
				}

				System.out.println();
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo: " + e.getMessage());
		}
	}
}