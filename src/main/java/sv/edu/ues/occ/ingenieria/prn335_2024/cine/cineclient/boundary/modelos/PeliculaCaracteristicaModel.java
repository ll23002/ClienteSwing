package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.PeliculaCaracteristica;

/**
 *
 * @author milag
 */
public class PeliculaCaracteristicaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Valor"};
    private List<PeliculaCaracteristica> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<PeliculaCaracteristica> lista) {
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
        PeliculaCaracteristica peliculacaracteristica = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return peliculacaracteristica.getIdPeliculaCaracteristica();
            case 1:
                return peliculacaracteristica.getValor();
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

    public PeliculaCaracteristica getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
