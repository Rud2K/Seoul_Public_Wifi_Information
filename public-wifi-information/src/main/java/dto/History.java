package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
	private Integer ID;				// ID
	private Double LAT;				// 위도
	private Double LNT;				// 경도
	private String INQUIRY_TIME;	// 조회일자
}
