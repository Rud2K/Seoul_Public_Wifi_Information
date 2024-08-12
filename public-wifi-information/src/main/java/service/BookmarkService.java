package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.BookmarkDAO;
import dto.Bookmark;
import dto.BookmarkGroup;

public class BookmarkService implements BookmarkServiceImpl {
	private BookmarkDAO bookmarkDAO;
	
	public BookmarkService() {
		this.bookmarkDAO = new BookmarkDAO();
	}
	
	@Override
	public String formattedDttm(LocalDateTime date) {
		final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");
		return date.format(FORMATTER);
	}
	
	@Override
	public LocalDateTime parseDateTime(String dateTimeStr) {
		return dateTimeStr == null || dateTimeStr.isEmpty()
				? null : LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	@Override
	public List<Bookmark> getBookmarkList() {
		List<Bookmark> bookmarkList = this.bookmarkDAO.getBookmarkList();
		
		for (Bookmark bookmark : bookmarkList) {
			LocalDateTime registerDttm = this.parseDateTime(bookmark.getREGISTER_DTTM());
			bookmark.setREGISTER_DTTM(this.formattedDttm(registerDttm));
		}
		
		return bookmarkList;
	}
	
	@Override
	public List<BookmarkGroup> getBookmarkGroupList() {
		List<BookmarkGroup> bookmarkGroupList = this.bookmarkDAO.getBookmarkGroupList();
		
		for (BookmarkGroup bookmarkGroup : bookmarkGroupList) {
			LocalDateTime registerDttm = this.parseDateTime(bookmarkGroup.getREGISTER_DTTM());
			bookmarkGroup.setREGISTER_DTTM(this.formattedDttm(registerDttm));
			LocalDateTime updateDttm = this.parseDateTime(bookmarkGroup.getUPDATE_DTTM());
			bookmarkGroup.setUPDATE_DTTM(updateDttm != null ? this.formattedDttm(updateDttm) : null);
		}
		
		return bookmarkGroupList;
	}
	
	@Override
	public BookmarkGroup getBookmarkGroup(int id) {
		BookmarkGroup bookmarkGroup = this.bookmarkDAO.getBookmarkGroup(id);
		
		LocalDateTime registerDttm = this.parseDateTime(bookmarkGroup.getREGISTER_DTTM());
		bookmarkGroup.setREGISTER_DTTM(this.formattedDttm(registerDttm));
		LocalDateTime updateDttm = this.parseDateTime(bookmarkGroup.getUPDATE_DTTM());
		bookmarkGroup.setUPDATE_DTTM(updateDttm != null ? this.formattedDttm(updateDttm) : null);
		
		return bookmarkGroup;
	}
	
	@Override
	public boolean addBookmarkGroup(String name, int orderNo) {
		return this.bookmarkDAO.addBookmarkGroup(name, orderNo);
	}
	
	@Override
	public boolean updateBookmarkGroup(int id, String name, int orderNo) {
		return this.bookmarkDAO.updateBookmarkGroup(id, name, orderNo);
	}
	
	@Override
	public boolean deleteBookmarkGroup(int groupId) {
		return this.bookmarkDAO.deleteBookmarkGroup(groupId);
	}
	
	@Override
	public boolean addBookmark(int groupId, String wifiMgrNo) {
		return this.bookmarkDAO.addBookmark(groupId, wifiMgrNo);
	}
	
	@Override
	public boolean deleteBookmark(int groupId) {
		return this.bookmarkDAO.deleteBookmark(groupId);
	}
	
	@Override
	public Bookmark getBookmark(int bookmarkId) {
		Bookmark bookmark = this.bookmarkDAO.getBookmark(bookmarkId);
		
		LocalDateTime registerDttm = this.parseDateTime(bookmark.getREGISTER_DTTM());
		bookmark.setREGISTER_DTTM(this.formattedDttm(registerDttm));
		
		return bookmark;
	}
}
