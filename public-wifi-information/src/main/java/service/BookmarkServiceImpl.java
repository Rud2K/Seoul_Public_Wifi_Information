package service;

import java.time.LocalDateTime;
import java.util.List;

import dto.BookmarkGroup;
import dto.Bookmark;

public interface BookmarkServiceImpl {
	public String formattedDttm(LocalDateTime date);
	public LocalDateTime parseDateTime(String dateTimeStr);
	public List<Bookmark> getBookmarkList();
	public List<BookmarkGroup> getBookmarkGroupList();
	public BookmarkGroup getBookmarkGroup(int id);
	public boolean addBookmarkGroup(String name, int orderNo);
	public boolean updateBookmarkGroup(int id, String name, int orderNo);
	public boolean deleteBookmarkGroup(int groupId);
	public boolean addBookmark(int groupId, String wifiMgrNo);
	public boolean deleteBookmark(int groupId);
	public Bookmark getBookmark(int bookmarkId);
}
