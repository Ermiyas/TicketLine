package at.ac.tuwien.inso.tl.dao;

public interface EntryDaoCustom {
	/**
	 * Findet den Entry mit uebereinstimmender seat_id und ueberprueft, ob das Entry bereits verkauft worden ist.
	 * @param seat_id Die Id eines Sitzes
	 * @return true wenn das Entry bereits verkauft wurde / false wenn nicht noch nicht verkauft wurde
	 */
	public Boolean isSold(Integer seat_id);
}
