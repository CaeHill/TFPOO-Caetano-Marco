package TFPOO.gestores;

import TFPOO.dados.*;
import java.util.*;

public class TransporteGestor {
    private List<Transporte> transportes = new LinkedList<>();
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

    public void adicionarTransporte(Transporte transporte) {
        transportes.add(transporte);
        if (transporte.getSituacao() == Estado.PENDENTE) {
            transportesPendentes.add(transporte);
        }
    }

    public void atualizarFilaPendentes() {
        transportesPendentes.clear();
        for (Transporte transporte : transportes) {
            if (transporte.getSituacao() == Estado.PENDENTE) {
                transportesPendentes.add(transporte);
            }
        }
    }

    public List<Transporte> getTodosTransportes() {
        return transportes;
    }

    public Queue<Transporte> getTransportesPendentes() {
        return transportesPendentes;
    }
}