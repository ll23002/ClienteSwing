package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoProducto;

/**
 *
 * @author milag
 */
public class TipoProductoModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Estado", "Comentarios"};
    private List<TipoProducto> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<TipoProducto> lista) {
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
        TipoProducto tipoproducto = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipoproducto.getIdTipoProducto();
            case 1:
                return tipoproducto.getNombre();
            case 2:
                return tipoproducto.getActivo();
            case 3:
                return tipoproducto.getComentarios();
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
            default:
                return Object.class;
        }
    }

    public TipoProducto getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
