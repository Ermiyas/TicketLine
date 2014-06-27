package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Row;

public interface RowDaoCustom {
	/**
	 * Liefert eine Liste aller Sitzplatzreihen, die den angegebenen Filterkriterien entspricht.
	 * @param showID Die ID einer Aufführung oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Sitzplatzreihen.
	 */
	public List<Row> findRows(Integer showID);
}
