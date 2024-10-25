// delete-button押下時のイベント設定
var elements = document.getElementsByClassName('delete-button');
Array.prototype.forEach.call(elements, function(item) {
  // itemを利用した処理
  item.onclick = function() {
    let result = window.confirm("タスクを削除してもよろしいですか？");
    if (!result) return false;
  };
}