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
