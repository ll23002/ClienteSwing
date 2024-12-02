package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoAsiento;

/**
 *
 * @author milag
 */
public class TipoAsientoModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Estado", "Comentarios", "ExpresionRegular"};
    private List<TipoAsiento> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<TipoAsiento> lista) {
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
        TipoAsiento tipoasiento = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipoasiento.getIdTipoAsiento();
            case 1:
                return tipoasiento.getNombre();
            case 2:
                return tipoasiento.getActivo();
            case 3:
                return tipoasiento.getComentarios();
            case 4:
                return tipoasiento.getExpresionRegular();
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
                return Boolean.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            default:
                return Object.class;
        }
    }

    public TipoAsiento getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
