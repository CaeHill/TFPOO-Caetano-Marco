package TFPOO.gestores;

import TFPOO.dados.*;
import java.util.*;

public class TransporteGestor {
    private Set<Transporte> transportes = new HashSet<>();
    private Queue<Transporte> transportesPendentes = new LinkedList<>();
    private List<Transporte> transportesAlocados = new LinkedList<>();

    public boolean cadastrarTransporte(Transporte transporte) {
        for (Transporte t : transportes) {
            if (t.getNumero() == transporte.getNumero()) {
                return false;
            }
        }
        transportes.add(transporte);
        return true;
    }

    public void adicionarTransportePendentes(Transporte transporte) {
        if (transporte.getSituacao() == Estado.PENDENTE) {
            transportesPendentes.add(transporte);
        }
    }

    public Queue<Transporte> getTransportesPendentes() {
        return transportesPendentes;
    }

    public void recolocarTransporteNaFilaPendentes(Transporte transporte) {
        if (transporte.getSituacao() == Estado.PENDENTE) {
            transportesPendentes.add(transporte);
        }
    }

    public void processarTransportesPendentes() {
        DroneGestor droneGestor = SistemaGestores.getDroneGestor();
        for (Transporte transporte : transportesPendentes) {
            boolean alocado = droneGestor.alocarDroneParaTransporte(transporte);
            if (alocado) {
                transporte.alocarDrone(transporte.getDrone());
                transportesAlocados.add(transporte);
            } else {
                recolocarTransporteNaFilaPendentes(transporte);
            }
        }
    }

    public boolean existemTransportesPendentes() {
        return !transportesPendentes.isEmpty();
    }

    public Set<Transporte> getTransportes() {
        return transportes;
    }
}