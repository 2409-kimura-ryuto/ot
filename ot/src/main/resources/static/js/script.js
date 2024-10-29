function CheckDelete(){
    let result = window.confirm("削除してもよろしいですか？");
    if (!result) return false;
}

//開始・停止切替処理
function toggleAction() {
    let submitButton = document.getElementByClassName('submitButton');
    let hidden = document.getElementByClassName('isStoppedNumber');

    if (submitButton.value === '停止') {
        submitButton.value = '開始';
        hidden.value = 0;
    } else {
        submitButton.value = '停止';
        hidden.value = 1;
    }
}