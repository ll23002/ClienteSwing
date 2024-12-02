package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Asiento;

/**
 *
 * @author milag
 */
public class AsientoModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Estado"};//se agregarian mas? como?
    private List<Asiento> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Asiento> lista) {
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
        Asiento asiento = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return asiento.getIdAsiento();
            case 1:
                return asiento.getNombre();
            case 2:
                return asiento.getActivo();
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
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    public Asiento getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
