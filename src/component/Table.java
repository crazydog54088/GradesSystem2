package component;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class Table extends JTable {
	
	private tableModel tableModel;
	private int cellMax = 100;
	private int cellMin = 0;
	
	public Table(String[] columns, String[][] data, boolean isCellEditable) {
		tableModel = new tableModel(columns, data, isCellEditable);
		super.setModel(tableModel);
		
		// 例外處理
		if (isCellEditable) {
			tableModel.addTableModelListener(new TableModelListener() {
			      public void tableChanged(TableModelEvent e) {
			    	  String[][] data = tableModel.getData();
			    	  int number = Integer.parseInt(data[e.getLastRow()][e.getColumn()]);
			    	  if (number > cellMax) data[e.getLastRow()][e.getColumn()] = cellMax + "";
			    	  else if (number < cellMin) data[e.getLastRow()][e.getColumn()] = cellMin + "";
			      }
			});
		}
	}

	public String[][] getTableData() {
		return tableModel.getData();
	}
	
	
	private static class tableModel extends AbstractTableModel {
		
		private String[] columns;
		private String[][] data;
		private boolean IsCellEditable;
		
		public tableModel(String[] columns, String[][] data, boolean isCellEditable) {
			this.columns = columns;
			this.data = data;
			this.IsCellEditable = isCellEditable;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return columns.length;
		}
		
		@Override
		public String getColumnName(int col){
			return columns[col];
		}

		@Override
		public Object getValueAt(int row, int column) {
			return data[row][column];
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			// 通常只有第三欄可以更改
			if (column == 2) return this.IsCellEditable;
			return false;
		}
		
		@Override
		public void setValueAt(Object value, int row, int column) {
		    data[row][column] = value + "";
		    fireTableCellUpdated(row,column);
		}
		
		public String[][] getData(){
			return data;
		}
		
	}
	
}
