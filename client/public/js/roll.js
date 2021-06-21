$(function() {
	// var content = $('section');
	// // var contentHeight = content.prevObject[0].body.scrollHeight
	// console.log(content)
	// console.log(content.prevObject.toString())
	// alert(content.prevObject.toString())
	// 总高度
	var bodyHeight = document.body.scrollHeight
	// 可视高度
	var clientH = window.innerHeight || document.documentElement.clientHeight
	var $window = $(window);
	var $roll = $('.roll');
	var sHeight = bodyHeight - clientH
	$window.on('scroll', function() {
		window.requestAnimationFrame(function() {
			var value = Math.max(0, Math.min(1, $window.scrollTop() / sHeight));
			updateProgressBar(value);
		});
	});

	function updateProgressBar(value) {
		$roll.css({
			width: value * 100 + '%'
		});
	}
});
