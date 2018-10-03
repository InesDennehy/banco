package banco;

import quick.dbtable.DBTable;

public class LoginInfo {
	private String status;
	private DBTable table;
	private long number;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public DBTable getTable() {
		return table;
	}
	public void setTable(DBTable table) {
		this.table = table;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
}
