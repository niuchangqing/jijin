package cn.com.jj.model;

public class Model {
	private Data Data;
	private int ErrCode;
	private String ErrMsg;
	private int TotalCount;
	private String Expansion;
	private int PageSize;
	private int PageIndex;

	public Data getData() {
		return Data;
	}

	public void setData(Data data) {
		Data = data;
	}

	public int getErrCode() {
		return ErrCode;
	}

	public void setErrCode(int errCode) {
		ErrCode = errCode;
	}

	public String getErrMsg() {
		return ErrMsg;
	}

	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}

	public int getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}

	public String getExpansion() {
		return Expansion;
	}

	public void setExpansion(String expansion) {
		Expansion = expansion;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}
}
