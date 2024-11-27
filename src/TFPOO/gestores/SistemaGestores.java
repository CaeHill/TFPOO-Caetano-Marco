package TFPOO.gestores;

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