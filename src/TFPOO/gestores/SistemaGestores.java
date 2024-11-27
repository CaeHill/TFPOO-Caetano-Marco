package TFPOO.gestores;

import TFPOO.dados.Drone;
import TFPOO.dados.Transporte;

import java.util.HashSet;
import java.util.Set;

public class SistemaGestores {
    private static DroneGestor droneGestor = new DroneGestor();
    private static TransporteGestor transporteGestor = new TransporteGestor();

    public static DroneGestor getDroneGestor() {
        return droneGestor;
    }

    public static TransporteGestor getTransporteGestor() {
        return transporteGestor;
    }
}