package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Reserva;

/**
 *
 * @author milag
 */
public class ReservaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Estado", "Fecha Reserva","Observaciones"};
    private List<Reserva> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Reserva> lista) {
        this.listaRegistros = lista != null ? lista : new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listaRegistros.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reserva reserva = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return reserva.getIdReserva();
            case 1:
                return reserva.getEstado();
            case 2:
                return reserva.getFechaReserva();
            case 3:
                return reserva.getObservaciones();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;//o double?
            case 3:
                return String.class;
            default:
                return Object.class;
        }
    }

    public Reserva getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
