package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkGroup {
	private Integer ID;					// ID
	private String NAME;				// 북마크 이름
	private Integer ORDER_NO;			// 순서
	private String REGISTER_DTTM;		// 등록일자
	private String UPDATE_DTTM;			// 수정일자
}
