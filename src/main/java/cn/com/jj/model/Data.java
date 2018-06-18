package cn.com.jj.model;

import java.util.List;

public class Data {
	private List<DetailModel> LSJZList;
	private String FundType;
	private String SYType;

	public List<DetailModel> getLSJZList() {
		return LSJZList;
	}

	public void setLSJZList(List<DetailModel> lSJZList) {
		LSJZList = lSJZList;
	}

	public String getFundType() {
		return FundType;
	}

	public void setFundType(String fundType) {
		FundType = fundType;
	}

	public String getSYType() {
		return SYType;
	}

	public void setSYType(String sYType) {
		SYType = sYType;
	}
}
