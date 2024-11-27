package TFPOO.gestores;

import TFPOO.dados.*;
import java.util.HashSet;
import java.util.Set;

public class DroneGestor {
    private Set<Drone> drones = new HashSet<>();

    public boolean alocarDroneParaTransporte(Transporte transporte) {
        for (Drone drone : drones) {
            if (podeAlocarDrone(drone, transporte)) {
                transporte.alocarDrone(drone);
                return true;
            }
        }
        return false;
    }

    private boolean podeAlocarDrone(Drone drone, Transporte transporte) {
        if (transporte instanceof TransportePessoal && drone instanceof DronePessoal) {
            return true;
        } else if (transporte instanceof TransporteCargaInanimada && drone instanceof DroneCargaInanimada) {
            return true;
        } else if (transporte instanceof TransporteCargaViva && drone instanceof DroneCargaViva) {
            return true;
        }
        return false;
    }

    public boolean cadastrarDrone(Drone drone) {
        for (Drone d : drones) {
            if (d.getCodigo() == drone.getCodigo()) {
                return false;
            }
        }
        drones.add(drone);
        return true;
    }

    public Set<Drone> getDrones() {
        return drones;
    }
}