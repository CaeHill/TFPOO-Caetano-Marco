package TFPOO.gestores;

import TFPOO.dados.Drone;
import java.util.HashSet;
import java.util.Set;

public class DroneGestor {
    private Set<Drone> drones = new HashSet<>();

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