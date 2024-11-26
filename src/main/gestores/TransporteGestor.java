package main.gestores;

import main.dados.*;
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
        transporte.calcularCusto(); // Calcula o custo antes de adicionar
        transportes.add(transporte); // Adiciona o transporte à lista
        return true;
    }

    public String listarTodosTransportes() {
        if (transportes.isEmpty()) {
            return "Nenhum transporte cadastrado.";
        }
        StringBuilder sb = new StringBuilder("Transportes Cadastrados:\n");
        for (Transporte t : transportes) {
            if (t instanceof TransportePessoal) {
                TransportePessoal tp = (TransportePessoal) t;
                sb.append(tp.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}