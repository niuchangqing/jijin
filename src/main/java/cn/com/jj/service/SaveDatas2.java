package cn.com.jj.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import cn.com.jj.model.DetailModel;

public class SaveDatas2 {
	private static Logger logger = LoggerFactory.getLogger(SaveDatas2.class);
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://18.191.214.185:3306/jijin?useUnicode=true&amp;characterEncoding=utf8";
	private static final String sql = "insert into t_jijin (FSRQ,DWJZ,LJJZ,SDATE,ACTUALSYI,NAVTYPE,JZZZL,SGZT,SHZT,FHFCZ,FHFCBZ,DTYPE,FHSP,code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "123456";

	private static DataSource dataSource = null;

	static {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("driverClassName", JDBC_DRIVER);
			map.put("url", DB_URL);
			map.put("username", USER);
			map.put("password", PASS);
			map.put("maxActive", "100");
			

			logger.error("初始化連接池");
			dataSource = DruidDataSourceFactory.createDataSource(map);
			logger.error("初始化完畢");
		} catch (Exception e) {
			logger.error("创建连接池异常", e);
		}
	}

	public static Connection getConn() throws SQLException {
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}

	public static void release(PreparedStatement pst, Connection conn) {
		try {
			pst.close();
			conn.close();
		} catch (Exception e) {
			logger.error("close连接异常", e);
		}
	}

	public static void save(List<DetailModel> details) {
		if (null == details || details.size() == 0) {
			return;
		}
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConn();
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
			logger.error("save [" + details.size() + "] successfully");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(pstmt, conn);
		}
	}
}
