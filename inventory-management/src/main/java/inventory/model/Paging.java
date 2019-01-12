package inventory.model;

public class Paging {
	private long totalRows;
	private int totalPages;
	private int indexPage;
	private int recordPerPage =10;
	private int offset;
	
	public Paging(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		if(totalRows>0) {
			totalPages =(int) Math.ceil(totalRows/(double)recordPerPage);
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public int getOffset() {
		if(indexPage>0) {
			offset = indexPage*recordPerPage - recordPerPage ;
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
