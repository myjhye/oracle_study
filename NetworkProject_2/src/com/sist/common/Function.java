package com.sist.common;

// 공유 => 서버 통해 작업 => 서버에서 지시
// 서버 = master, 클라이언트 : 슬레이브
// 기능별 분리(숫자) => 서버가 처리 => 내부 프로토콜

public class Function {
	public static final int LOGIN = 100;	// 로그인된 사람
	public static final int MYLOG = 110;	// 로그인하는 사람
	public static final int CHAT=200;		// 채팅
	public static final int SEND=300;		// 쪽지 보내지
	public static final int END=900;		// 남아있는 사람
	public static final int MYEND=910;		// 나가는 사람
	
}
