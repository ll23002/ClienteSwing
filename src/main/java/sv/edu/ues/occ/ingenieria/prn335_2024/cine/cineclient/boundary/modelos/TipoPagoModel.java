package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoPago;

/**
 *
 * @author milag
 */
public class TipoPagoModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Estado"};
    private List<TipoPago> listaRegistros = new ArrayList<>();

    public void setListaRegistros(List<TipoPago> lista) {
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
        TipoPago tipopago = listaRegistros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tipopago.getIdTipoPago();
            case 1:
                return tipopago.getNombre();
            case 2:
                return tipopago.getActivo();
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
            default:
                return Object.class;
        }
    }

    public TipoPago getRegistro(int rowIndex) {
        return listaRegistros.get(rowIndex);
    }
}
