// 게시글 등록 성공 메시지
$(document).ready(function() {
    $('#createPostForm').submit(function(e) {
        e.preventDefault();

        $.ajax({
            type: 'POST',
            url: '/board/posts',
            data: $(this).serialize(),
            success: function(response) {
                alert('게시글을 등록했습니다.');
                location.href = '/board';
            },
            error: function(error) {
                alert('게시글 등록에 실패했습니다.');
            }
        });
    });
});

// 게시글 수정 성공 메시지
$(document).ready(function() {
    $('#updatePostForm').submit(function(e) {
        e.preventDefault();

        var boardId = $('[name=board_id]').val();

        $.ajax({
            type: 'PUT',
            url: '/board/' + boardId,
            data: $(this).serialize(),
            success: function(response) {
                alert('게시글이 수정되었습니다.');
                location.href = '/board';
            },
            error: function(error) {
                alert('게시글 수정에 실패했습니다.');
            }
        });
    });
});

// 게시글 삭제 버튼
$(document).ready(function() {
    $('.delete-btn').click(function() {
        var boardId = $(this).data('board-id');

        if (confirm('정말로 삭제하시겠습니까?')) {
            $.ajax({
                type: 'DELETE',
                url: '/board/' + boardId,
                success: function(response) {
                    alert('게시글이 삭제되었습니다.');
                    location.href = '/board';
                },
                error: function(error) {
                    alert('게시글 삭제에 실패했습니다.');
                }
            });
        }
    });
});

// 목록 이동버튼
function goToBoardList() {
    window.location.href = "/board";
}

// 취소버튼
function cancelEdit() {
    window.location.href = "/board";
}

// 글쓰기 폼 이동
function goToPostForm() {
    window.location.href = "/board/posts"
}