package TFPOO.gestores;

import TFPOO.dados.*;
import java.util.HashSet;
import java.util.Set;

public class TransporteGestor {
    private Set<Transporte> transportes = new HashSet<>();

    public boolean cadastrarTransporte(Transporte transporte) {
        for (Transporte t : transportes) {
            if (t.getNumero() == transporte.getNumero()) {
                return false; // Número já cadastrado
            }
        }
        transportes.add(transporte);
        return true;
    }
}