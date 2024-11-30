package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoSala;

public class TipoSalaModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Activo", "Comentarios", "Expresión Regular"};
    private List<TipoSala> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<TipoSala> lista) {
        this.listaRegistros = lista != null ? lista : new ArrayList<>();
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
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
        TipoSala tipoSala = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipoSala.getIdTipoSala();
            case 1:
                return tipoSala.getNombre();
            case 2:
                return tipoSala.getActivo();
            case 3:
                return tipoSala.getComentarios();
            case 4:
                return tipoSala.getExpresionRegular();
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
        // Define el tipo de dato esperado para cada columna
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
            case 3:
            case 4:
                return String.class;
            case 2:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    public TipoSala getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
