/* enum 모아둘 공간: constant */
/* 아이템의 판매 상태 지정하는 부분:품절 또는 판매중인지 구분 */

package com.farmer.constant;

public enum ItemSellStatus {
    SELL, SOLD_OUT
}

/* 프로젝트 중간에 다시 생성함*/
/* 이유: ShopApplication을 올리고나서 한동안은 itemMng.html 페이지가 웹 화면에 떴다.
그러나 일정 시간이 지나면 콘솔에 아래와 같은 메세지가 뜨면서 Whitelabel Error Page(status=500) 페이지가 화면에 로드됐다.
해결방안을 못 찾고 있다가 com.shop.constant.ItemSellStatus.java enum 파일을 삭제했다 다시 생성하니 오류가 발생하지 않았다.
찾지 못한 오타나 공백이 포함되어 있을 것으로 추정 중이나 정확한 원인은 파악하지 못했다.*/
