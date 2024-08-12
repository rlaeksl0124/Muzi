package com.Toy2.Notice.entity;

import java.util.HashMap;
import java.util.Map;

public class FaqCategory {
    private static final Map<Integer, String> categoryMap = new HashMap<>();

    static {
        // Product Information
        categoryMap.put(101, "소파류");
        categoryMap.put(102, "침대류");
        categoryMap.put(103, "테이블류");
        categoryMap.put(104, "수납선반류");
        categoryMap.put(105, "전자용품");
        categoryMap.put(106, "패브릭");
        categoryMap.put(107, "배송관련");
        categoryMap.put(108, "기타");

        // Membership Benefits/Services
        categoryMap.put(201, "L.POINT 회원");
        categoryMap.put(202, "메일매거진");
        categoryMap.put(203, "회원헤택");
        categoryMap.put(204, "회원등급");
        categoryMap.put(205, "상품평");
        categoryMap.put(206, "L-money");
        categoryMap.put(207, "L.POINT");
        categoryMap.put(208, "카탈로그");

        // Member Information
        categoryMap.put(301, "정보변경");
        categoryMap.put(302, "아이디/비밀번호");
        categoryMap.put(303, "탈퇴");

        // Order/Payment Methods
        categoryMap.put(401, "주문 방법 및 확인");
        categoryMap.put(402, "대량구매");
        categoryMap.put(403, "주문 내용 변경");
        categoryMap.put(404, "입금확인");
        categoryMap.put(405, "세트판매확인");
        categoryMap.put(406, "결제수단");
        categoryMap.put(407, "소량재고");

        // Cancellation/Exchange/Return
        categoryMap.put(501, "반품");
        categoryMap.put(502, "주문취소");
        categoryMap.put(503, "교환/AS방법");

        // Delivery Related
        categoryMap.put(601, "배송확인/기간");
        categoryMap.put(602, "배송지변경");
        categoryMap.put(603, "배송기간");

        // Receipt
        categoryMap.put(701, "현금영수증");
        categoryMap.put(702, "신용카드 매출전표");

        // Site Usage Inquiries
        categoryMap.put(801, "PC문제해결/오류");
        categoryMap.put(802, "MUJI 고객센터 이용 안내");
    }

    public static String getCategoryName(Integer cate_no) {
        return categoryMap.get(cate_no);
    }

}
