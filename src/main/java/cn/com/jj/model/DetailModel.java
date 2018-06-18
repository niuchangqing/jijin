package cn.com.jj.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DetailModel {
	private String ID;
	private String code;
	private String FSRQ;
	private String DWJZ;
	private String LJJZ;
	private String SDATE;
	private String ACTUALSYI;
	private String NAVTYPE;
	private String JZZZL;
	private String SGZT;
	private String SHZT;
	private String FHFCZ;
	private String FHFCBZ;
	private String DTYPE;
	private String FHSP;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getFSRQ() {
		return FSRQ;
	}

	public void setFSRQ(String fSRQ) {
		FSRQ = fSRQ;
	}

	public String getDWJZ() {
		return DWJZ;
	}

	public void setDWJZ(String dWJZ) {
		DWJZ = dWJZ;
	}

	public String getLJJZ() {
		return LJJZ;
	}

	public void setLJJZ(String lJJZ) {
		LJJZ = lJJZ;
	}

	public String getSDATE() {
		return SDATE;
	}

	public void setSDATE(String sDATE) {
		SDATE = sDATE;
	}

	public String getACTUALSYI() {
		return ACTUALSYI;
	}

	public void setACTUALSYI(String aCTUALSYI) {
		ACTUALSYI = aCTUALSYI;
	}

	public String getNAVTYPE() {
		return NAVTYPE;
	}

	public void setNAVTYPE(String nAVTYPE) {
		NAVTYPE = nAVTYPE;
	}

	public String getJZZZL() {
		return JZZZL;
	}

	public void setJZZZL(String jZZZL) {
		JZZZL = jZZZL;
	}

	public String getSGZT() {
		return SGZT;
	}

	public void setSGZT(String sGZT) {
		SGZT = sGZT;
	}

	public String getSHZT() {
		return SHZT;
	}

	public void setSHZT(String sHZT) {
		SHZT = sHZT;
	}

	public String getFHFCZ() {
		return FHFCZ;
	}

	public void setFHFCZ(String fHFCZ) {
		FHFCZ = fHFCZ;
	}

	public String getFHFCBZ() {
		return FHFCBZ;
	}

	public void setFHFCBZ(String fHFCBZ) {
		FHFCBZ = fHFCBZ;
	}

	public String getDTYPE() {
		return DTYPE;
	}

	public void setDTYPE(String dTYPE) {
		DTYPE = dTYPE;
	}

	public String getFHSP() {
		return FHSP;
	}

	public void setFHSP(String fHSP) {
		FHSP = fHSP;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
