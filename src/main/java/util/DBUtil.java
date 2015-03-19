package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	public synchronized static int insert(int i) {
		Connection conn = DBConnectionManager.getConnByJdbc();
		if (conn == null)
			return -1;
		try {
			conn.setTransactionIsolation(8);
			conn.setAutoCommit(false);
			PreparedStatement ps1 = conn
					.prepareStatement("select count(1) from log where id=?");
			ps1.setInt(1, i);
			ResultSet rs = ps1.executeQuery();
			if (rs.next()) {
				int rs1 = rs.getInt(1);
				if (rs1 == 0) {
					ps1 = conn.prepareStatement("insert into log(id) values(?)");
					ps1.setInt(1, i);
					int rows = ps1.executeUpdate();
					System.out.println(String.format("rows:%d, i:%d ",  rows, i));
				}
			}
			rs.close();
			ps1.close();
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return 1;
	}
}
