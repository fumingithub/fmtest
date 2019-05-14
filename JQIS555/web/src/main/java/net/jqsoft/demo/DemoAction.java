package net.jqsoft.demo;

import com.opensymphony.xwork2.ActionSupport;

public class DemoAction extends ActionSupport {
	protected int start = 1;
	protected int limit;
	protected long totalCount;

	/**
	 * serialVersionUID:TODO
	 * 
	 * @since Ver 1.1
	 */
	private static final long serialVersionUID = 5537071183099132377L;

	public String versionRelease() {

		return "versionRelease";
	}
	
	public String choiceVersion() {

		return "choiceVersion";
	}
	
	public String audit() {
		return "audit";
	}
	
	
	public String importVer(){
		return "importVer";
	}
	
	public String verReleaseEdit(){
		return "verReleaseEdit";
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
