package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Sala;

/**
 *
 * @author milag
 */
public class SalaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Observaciones", "Estado"};
    private List<Sala> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Sala> lista) {
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
        Sala sala = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sala.getIdSala();
            case 1:
                return sala.getNombre();
            case 2:
                return sala.getObservaciones();
            case 3:
                return sala.getActivo();

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
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    public Sala getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
