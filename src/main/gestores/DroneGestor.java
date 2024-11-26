package main.gestores;

/*import dados.*;
import java.util.HashSet;
import java.util.Set;

public class DroneGestor {
    private Set<Drone> drones = new HashSet<>();

    public boolean cadastrarTransporte(Drone drone) {
        for (Drone d : drones) {
            if (d.getNumero() == drone.getNumero()) {
                return false; // Número já cadastrado
            }
        }
        drone.calcularCusto(); // Calcula o custo antes de adicionar
        drones.add(drone); // Adiciona o transporte à lista
        return true;
    }

    public String listarTodosDrone() {
        if (drones.isEmpty()) {
            return "Nenhum transporte cadastrado.";
        }
        StringBuilder sb = new StringBuilder("Transportes Cadastrados:\n");
        for (Transporte t : drones) {
            if (t instanceof TransportePessoal) {
                TransportePessoal tp = (TransportePessoal) t;
                sb.append(tp.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}*/