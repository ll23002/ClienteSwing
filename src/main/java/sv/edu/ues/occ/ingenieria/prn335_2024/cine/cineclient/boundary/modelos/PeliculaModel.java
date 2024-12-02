package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Pelicula;

/**
 *
 * @author milag
 */
public class PeliculaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Sinopsis"};
    private List<Pelicula> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Pelicula> lista) {
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
        Pelicula pelicula = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pelicula.getIdPelicula();
            case 1:
                return pelicula.getNombre();
            case 2:
                return pelicula.getSinopsis();
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
                return String.class;
            default:
                return Object.class;
        }
    }

    public Pelicula getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
