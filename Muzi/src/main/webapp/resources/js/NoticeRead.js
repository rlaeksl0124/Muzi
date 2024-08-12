$(document).ready(function() {
    // NoticeTitle을 클릭했을 때 이벤트 처리
    $('.NoticeTitle').click(function () {
        // 클릭된 요소의 다음 tr의 NoticeContents 요소를 찾습니다.
        let contentElement = $(this).parent().next('.NoticeContents').find('td');

        if (contentElement.html().trim() !== "") {
            contentElement.empty();
            return;
        }

        // NoticeContents 요소를 모두 비웁니다.
        $('.NoticeContents td').empty();

        // 클릭된 Notice의 번호를 가져옵니다.
        let noticeNo = $(this).parent().find('td:first').text();

        // Ajax 요청을 보냅니다.
        $.ajax({
            url: '/Getnotice',
            type: 'GET',
            data: {no: noticeNo},
            success: function (data) {
                // 서버에서 반환된 n_contents 데이터를 contentElement에 삽입합니다.
                contentElement.html(data.n_contents);
            },
            error: function () {
                alert('게시글을 읽어오지 못했습니다.');
            }
        });
    });
    $(document).click(function (e) {
        let target = $(e.target);

        // 클릭한 요소가 게시글 영역이 아니라면
        if (!target.closest('.NoticeTitle').length && !target.closest('.NoticeContents').length) {
            $('.NoticeContents').find('td').empty(); // 내용을 비움
        }
    });
    $('.deleteModify').click(function () {
        let noticeNo = $(this).parent().parent().find('td:first').text();

    });
        $(document).ready(function () {
        let noticeNo;
        let page;

        $('.deleteModify').click(function () {
        // Notice 번호와 페이지 번호를 가져옵니다.
        noticeNo = $(this).parent().parent().find('td:first').text().trim();
        let params = new URLSearchParams(window.location.search);
        page = params.get('page') || 1;

        // 작업 선택 모달을 엽니다.
        $('#actionModal').show();
    });

        // 모달 창 닫기
        $('.close').click(function () {
        $('.modal').hide();
    });

        // 수정 버튼 클릭 시
        $('#confirmModify').click(function () {
        window.location.href = `/modify?no=${noticeNo}&page=${page}`;
    });

        // 삭제 버튼 클릭 시 삭제 확인 모달을 엽니다.
        $('#confirmDelete').click(function () {
        $('#actionModal').hide();
        $('#confirmDeleteModal').show();
    });

        // 최종 삭제 확인 시
        $('#finalDelete').click(function () {
        window.location.href = `/noticeDelete?no=${noticeNo}&page=${page}`;
    });

        // 삭제 취소 시
        $('#cancelDelete').click(function () {
        $('#confirmDeleteModal').hide();
    });

        // 모달 외부 클릭 시 닫기
        $(window).click(function (event) {
        if ($(event.target).hasClass('modal')) {
        $('.modal').hide();
    }
    });
    });
});
