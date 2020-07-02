package cn.edu.zucc.booklib.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BeanReader {
	private String readerid;
	private String readerName;
	private int readerTypeId;
	private int lendBookLimitted;
	private Date createDate; //注册时间
	private String creatorUserId; //注册操作执行人
	private Date removeDate; //注销时间
	private String removerUserId; //注销操作执行人
	private Date stopDate;  //挂失时间
	private String stopUserId; //挂失操作执行人

	private List<BeanBook> notReturnBook = new ArrayList<BeanBook>();

	private String readerTypeName;//读者类别，存储在ReaderType表中，读者表中只存储readerTypeId，程序应根据readerTypeId获取该值

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public int getReaderTypeId() {
		return readerTypeId;
	}

	public void setReaderTypeId(int readerTypeId) {
		this.readerTypeId = readerTypeId;
	}

	public int getLendBookLimitted() {
		return lendBookLimitted;
	}

	public void setLendBookLimitted(int lendBookLimitted) {
		this.lendBookLimitted = lendBookLimitted;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatorUserId() {
		return creatorUserId;
	}

	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public String getRemoverUserId() {
		return removerUserId;
	}

	public void setRemoverUserId(String removerUserId) {
		this.removerUserId = removerUserId;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public String getStopUserId() {
		return stopUserId;
	}

	public void setStopUserId(String stopUserId) {
		this.stopUserId = stopUserId;
	}

	public String getReaderTypeName() {
		return readerTypeName;
	}

	public void setReaderTypeName(String readerTypeName) {
		this.readerTypeName = readerTypeName;
	}

	@Override
	public String toString() {
		return "BeanReader{" +
				"readerid='" + readerid + '\'' +
				", readerName='" + readerName + '\'' +
				", readerTypeId=" + readerTypeId +
				", lendBookLimitted=" + lendBookLimitted +
				", createDate=" + createDate +
				", creatorUserId='" + creatorUserId + '\'' +
				", removeDate=" + removeDate +
				", removerUserId='" + removerUserId + '\'' +
				", stopDate=" + stopDate +
				", stopUserId='" + stopUserId + '\'' +
				", readerTypeName='" + readerTypeName + '\'' +
				'}';
	}

	public List<BeanBook> getNotReturnBook() {
		return notReturnBook;
	}

	public void setNotReturnBook(List<BeanBook> notReturnBook) {
		this.notReturnBook = notReturnBook;
	}
}
