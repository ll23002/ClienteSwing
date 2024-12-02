package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.AsientoCaracteristica;

/**
 *
 * @author milag
 */
public class AsientoCaracteristicaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Valor"};
    private List<AsientoCaracteristica> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<AsientoCaracteristica> lista) {
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
        AsientoCaracteristica asientocaracteristica = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return asientocaracteristica.getIdAsientoCaracteristica();
            case 1:
                return asientocaracteristica.getValor();
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
            default:
                return Object.class;
        }
    }

    public AsientoCaracteristica getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
