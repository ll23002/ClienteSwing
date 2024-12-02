package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Programacion;

/**
 *
 * @author milag
 */
public class ProgramacionModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Desde", "Hasta", "Comentarios"};
    private List<Programacion> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<Programacion> lista) {
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
        Programacion programacion = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return programacion.getIdProgramacion();
            case 1:
                return programacion.getDesde();
            case 2:
                return programacion.getHasta();
            case 3:
                return programacion.getComentarios();
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
            case 3:
                return String.class;
            default:
                return Object.class;
        }
    }

    public Programacion getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
