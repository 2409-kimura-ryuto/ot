//削除のボタンのアラート
function CheckDelete(){
    let result = window.confirm("削除してもよろしいですか？");
    if (!result) return false;
}

//開始・停止切替の際のアラート
function CheckChange(){
    let result = window.confirm("変更してもよろしいですか？");
    if (!result) return false;
}

$(document).ready(function() {
    $(".message-icon").mouseenter(function() {
        $(this).css({
            "background-color": "#f0f0f0",
            "box-shadow": "0 4px 10px rgba(0, 0, 0, 0.2)",
            "transform": "translateY(-5px)"
        });
    });

    $(".message-icon").mouseleave(function() {
        $(this).css({
            "background-color": "",
            "box-shadow": "",
            "transform": ""
        });
    });
});

$(document).ready(function() {
    $("#messageNew").on("submit", function() {
        alert("投稿を行いました。TOP画面で確認をしてください");
    });
});

$(document).ready(function() {
    $('#tooltipTitle').mouseenter(function(event) {
        //ツールチップを表示
        $('.tooltip-title').css({
            'display': 'block',
            'left': event.pageX + 'px',
            'top': event.pageY + 20 + 'px'  //少し下に表示
        });
    });

    $('#tooltipTitle').mouseleave(function() {
        //ツールチップを非表示
        $('.tooltip-title').css('display', 'none');
    });
});

$(document).ready(function() {
    $('#tooltipText').mouseenter(function(event) {
        //ツールチップを表示
        $('.tooltip-text').css({
            'display': 'block',
            'left': event.pageX + 'px',
            'top': event.pageY + 20 + 'px'
        });
    });

    $('#tooltipText').mouseleave(function() {
        //ツールチップを非表示
        $('.tooltip-text').css('display', 'none');
    });
});

$(document).ready(function() {
    $('#tooltipCategory').mouseenter(function(event) {
        //ツールチップを表示
        $('.tooltip-category').css({
            'display': 'block',
            'left': event.pageX + 'px',
            'top': event.pageY + 20 + 'px'
        });
    });

    $('#tooltipCategory').mouseleave(function() {
        //ツールチップを非表示
        $('.tooltip-category').css('display', 'none');
    });
});