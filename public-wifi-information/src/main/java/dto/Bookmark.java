package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bookmark {
	private Integer ID;				// ID
	private Integer GROUP_ID;		// 북마크 그룹 번호
	private String NAME;			// 북마크 그룹 이름
	private String X_SWIFI_MGR_NO;	// 와이파이 고유번호
	private String X_SWIFI_MAIN_NM;	// 와이파이명
	private String REGISTER_DTTM;	// 등록일자
}
