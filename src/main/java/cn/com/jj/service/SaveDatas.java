package cn.com.jj.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.jj.model.DetailModel;

public class SaveDatas {
	private static Logger logger = LoggerFactory.getLogger(SaveDatas.class);
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://18.191.214.185:3306/jijin?useUnicode=true&amp;characterEncoding=utf8";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "123456";

	public static void save(List<DetailModel> details) {
		if (null == details || details.size() == 0) {
			return;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into t_jijin (FSRQ,DWJZ,LJJZ,SDATE,ACTUALSYI,NAVTYPE,JZZZL,SGZT,SHZT,FHFCZ,FHFCBZ,DTYPE,FHSP,code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for (DetailModel detailModel : details) {
				String code = detailModel.getCode();
				String FSRQ = StringUtils.trimToEmpty(detailModel.getFSRQ());
				String DWJZ = StringUtils.trimToEmpty(detailModel.getDWJZ());
				String LJJZ = StringUtils.trimToEmpty(detailModel.getLJJZ());
				String SDATE = StringUtils.trimToEmpty(detailModel.getSDATE());
				String ACTUALSYI = StringUtils.trimToEmpty(detailModel.getACTUALSYI());
				String NAVTYPE = StringUtils.trimToEmpty(detailModel.getNAVTYPE());
				String JZZZL = StringUtils.trimToEmpty(detailModel.getJZZZL());
				String SGZT = StringUtils.trimToEmpty(detailModel.getSGZT());
				String SHZT = StringUtils.trimToEmpty(detailModel.getSHZT());
				String FHFCZ = StringUtils.trimToEmpty(detailModel.getFHFCZ());
				String FHFCBZ = StringUtils.trimToEmpty(detailModel.getFHFCBZ());
				String DTYPE = StringUtils.trimToEmpty(detailModel.getDTYPE());
				String FHSP = StringUtils.trimToEmpty(detailModel.getFHSP());

				pstmt.setString(1, FSRQ);
				pstmt.setString(2, DWJZ);
				pstmt.setString(3, LJJZ);
				pstmt.setString(4, SDATE);
				pstmt.setString(5, ACTUALSYI);
				pstmt.setString(6, NAVTYPE);
				pstmt.setString(7, JZZZL);
				pstmt.setString(8, SGZT);
				pstmt.setString(9, SHZT);
				pstmt.setString(10, FHFCZ);
				pstmt.setString(11, FHFCBZ);
				pstmt.setString(12, DTYPE);
				pstmt.setString(13, FHSP);
				pstmt.setString(14, code);
				// Add it to the batch
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			logger.error("Database created successfully...");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
}
