package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Sucursal;

/**
 *
 * @author milag
 */
public class SucursalModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Coordenadas", "Estado", "Comentarios"};
    private List<Sucursal> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Sucursal> lista) {
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
        Sucursal sucursal = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sucursal.getIdSucursal();
            case 1:
                return sucursal.getNombre();
            case 2:
                return (sucursal.getLatitud() + "," + sucursal.getLongitud());
            case 3:
                return sucursal.getActivo();
            case 4:
                return sucursal.getComentarios();
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
                return String.class;//o double?
            case 3:
                return Boolean.class;
            case 4:
                return String.class;
            default:
                return Object.class;
        }
    }

    public Sucursal getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
