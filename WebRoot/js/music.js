(function($){

	// Settings
	var repeat = Storage.repeat || 0,
		shuffle = Storage.shuffle || 'false',
		continous = true,
		autoplay = true,
		odata = new Array(),
		playlist =new Array(),
		page=1,
		commend = new Array(),
		init = true;
	//创建播放列表数组
	var createPlaylist = function(odata){
		if(sessionStorage.playlist!=null)
			{
				playlist = JSON.parse(sessionStorage.playlist);
			}
			else
			{
				for(var i=0;i<odata.length;i++)
					{
						var obj = {
								title: odata[i].songName,
								artist: odata[i].singer,
								album: odata[i].albumName,
								cover:odata[i].albumImg,
								mp3: odata[i].m4aUrl,
								ogg: ''
							};
						playlist[i]=obj;
					}
					sessionStorage.setItem('playlist',JSON.stringify(playlist));
			}
	}
		
	//对&#49888;&#50857;&#51116; html实体字符转换
	var decode =function(str) {
		return str.replace(/&#(\d+);/g, function(match, dec) {
			return String.fromCharCode(dec);
		});
	}
	
	//超出文本省略号替换
	var textflow = function(str)
	{
		if(str.length>25)
		{
			return str.substring(0,18)+'...';
		}
		return str;
	}

		


	// 生成右侧歌单列表
		var loadplaylist = function(index){
			var _i = parseInt(index);
			var a=0,max=7,_index=0;
			if(_i<3){a=0;max=7}
			else if(_i>playlist.length-4){a=playlist.length-7;max=playlist.length;}
			else{a=_i-3;max=_i+4;}
			for(a;a<max;a++)
			{
				if(a>=playlist.length) return;
				var item = playlist[a];
				$('#playlist li:eq('+_index+')').attr('index',a).text(item.artist+' - '+item.title).removeClass('playing');
				_index++;
				if(_index>6) return;
			}
		}
	

	var time = new Date(),
		currentTrack = shuffle === 'true' ? time.getTime() % playlist.length : 0,
		trigger = false,
		audio, timeout, isPlaying, playCounts;

	var play = function(){
		audio.play();
		$('.playback').addClass('playing');
		timeout = setInterval(updateProgress, 500);
		isPlaying = true;
	}

	var pause = function(){
		audio.pause();
		$('.playback').removeClass('playing');
		clearInterval(updateProgress);
		isPlaying = false;
	}

	// Update progress
	var setProgress = function(value){
		var currentSec = parseInt(value%60) < 10 ? '0' + parseInt(value%60) : parseInt(value%60),
			ratio = value / audio.duration * 100;

		$('.timer').html(parseInt(value/60)+':'+currentSec);
		$('.progress .pace').css('width', ratio + '%');
		$('.progress .slider a').css('left', ratio + '%');
	}

	var updateProgress = function(){
		setProgress(audio.currentTime);
	}

	// Progress slider
	$('.progress .slider').slider({step: 0.1, slide: function(event, ui){
		$(this).addClass('enable');
		setProgress(audio.duration * ui.value / 100);
		clearInterval(timeout);
	}, stop: function(event, ui){
		audio.currentTime = audio.duration * ui.value / 100;
		$(this).removeClass('enable');
		timeout = setInterval(updateProgress, 500);
	}});

	// Volume slider
	var setVolume = function(value){
		audio.volume = localStorage.volume = value;
		$('.volume .pace').css('width', value * 100 + '%');
		$('.volume .slider a').css('left', value * 100 + '%');
	}

	var volume = localStorage.volume || 0.5;
	$('.volume .slider').slider({max: 1, min: 0, step: 0.01, value: volume, slide: function(event, ui){
		setVolume(ui.value);
		$(this).addClass('enable');
		$('.mute').removeClass('enable');
	}, stop: function(){
		$(this).removeClass('enable');
	}}).children('.pace').css('width', volume * 100 + '%');

	$('.mute').click(function(){
		if ($(this).hasClass('enable')){
			setVolume($(this).data('volume'));
			$(this).removeClass('enable');
		} else {
			$(this).data('volume', audio.volume).addClass('enable');
			setVolume(0);
		}
	});

	// Switch track
	var switchTrack = function(i){
		if (i < 0){
			track = currentTrack = playlist.length - 1;
		} else if (i >= playlist.length){
			track = currentTrack = 0;
		} else {
			track =currentTrack =i;
		}
		$('audio').remove();
		loadMusic(track);
		if (isPlaying == true) play();
	}

	// Shuffle
	var shufflePlay = function(){
		var time = new Date(),
			lastTrack = currentTrack;
		currentTrack = time.getTime() % playlist.length;
		if (lastTrack == currentTrack) ++currentTrack;
		switchTrack(currentTrack);
	}

	// Fire when track ended
	var ended = function(){
		pause();
		audio.currentTime = 0;
		playCounts++;
		if (continous == true) isPlaying = true;
		if (repeat == 1){
			play();
		} else {
			if (shuffle === 'true'){
				shufflePlay();
			} else {
				if (repeat == 2){
					switchTrack(++currentTrack);
				} else {
					if (currentTrack < playlist.length) switchTrack(++currentTrack);
				}
			}
		}
	}

	var beforeLoad = function(){
		var endVal = this.seekable && this.seekable.length ? this.seekable.end(0) : 0;
		$('.progress .loaded').css('width', (100 / (this.duration || 1) * endVal) +'%');
	}

	// Fire when track loaded completely
	var afterLoad = function(){
		if (autoplay == true) play();
	}

	// Load track
	var loadMusic = function(i){
		loadplaylist(i);
		var item = playlist[i],
			newaudio = $('<audio>').html('<source src="'+item.mp3+'"><source src="'+item.ogg+'">').appendTo('#player');
		$("#player").css('background','url('+item.cover+')');
		document.getElementById('player').style.backgroundPosition='center';
		$('.tag').html('<strong>'+decode(item.title)+'</strong><span class="artist">'+decode(item.artist)+'</span><span class="album">'+decode(item.album)+'</span>');
		$(".album").text(decode(item.album));
		$('#playlist .playing').removeClass();
		$('#playlist li[index='+i+']').removeClass('playing').addClass('playing');
		audio = newaudio[0];
		audio.volume = $('.mute').hasClass('enable') ? 0 : volume;
		audio.addEventListener('progress', beforeLoad, false);
		audio.addEventListener('durationchange', beforeLoad, false);
		audio.addEventListener('canplay', afterLoad, false);
		audio.addEventListener('ended', ended, false);
	}
	
	$('.playback').on('click', function(){
		if ($(this).hasClass('playing')){
			pause();
		} else {
			play();
		}
	});
	
	$('.rewind').on('click', function(){
		if (shuffle === 'true'){
			shufflePlay();
		} else {
			switchTrack(--currentTrack);
		}
	});
	
	$('.fastforward').on('click', function(){
		if (shuffle === 'true'){
			shufflePlay();
		} else {
			switchTrack(++currentTrack);
		}
	});
	
	if (shuffle === 'true') $('.shuffle').addClass('enable');
	if (repeat == 1){
		$('.repeat').addClass('once');
	} else if (repeat == 2){
		$('.repeat').addClass('all');
	}

	$('.repeat').on('click', function(){
		if ($(this).hasClass('once')){
			repeat = localStorage.repeat = 2;
			$(this).removeClass('once').addClass('all');
		} else if ($(this).hasClass('all')){
			repeat = localStorage.repeat = 0;
			$(this).removeClass('all');
		} else {
			repeat = localStorage.repeat = 1;
			$(this).addClass('once');
		}
	});

	$('.shuffle').on('click', function(){
		if ($(this).hasClass('enable')){
			shuffle = localStorage.shuffle = 'false';
			$(this).removeClass('enable');
		} else {
			shuffle = localStorage.shuffle = 'true';
			$(this).addClass('enable');
		}
	});
	
	
	//右侧歌单点击事件
	$('#playlist li').each(function(){
		$(this).on('click',function(){
			switchTrack($(this).attr('index'));
		});
	});
	
	//将odata结果集的某个歌曲数据转成playlist中的数据
	var toplaylist = function(odata,index){
		var obj = {
				title: odata[index].songName,
				artist: odata[index].singer,
				album: odata[index].albumName,
				cover:odata[index].albumImg,
				mp3: odata[index].m4aUrl,
				ogg: ''
			};
		return obj;
	}
	
	//content里列表播放按钮点击
	$('.playbtn').each(function(i){
		$(this).on('click',function(){
			var index = $(this).attr('index');
			if(init) //初始数据直接索引播放
			{
				switchTrack(index);
			}
			else  //非初始数据先追加数据到播放列表数组再播放
			{
				switchTrack(playlist.push(toplaylist(odata,parseInt(index)))-1);
				sessionStorage.setItem('playlist',JSON.stringify(playlist));
			}
		})
		
	});
	
	//content里列表下一首播放按钮点击
	$('.addbtn').each(function(){
		$(this).on('click',function(){
			var nowindex = parseInt($("[class='playing']").attr('index'));
			var addindex = parseInt($(this).attr('index'));  //需要添加的数据在odata结果集中的索引
			if(init){	playlist.splice(nowindex+1,0,playlist[addindex]);}
			else{	playlist.splice(nowindex+1,0,toplaylist(odata,addindex));}
			loadplaylist(nowindex);
			$('#playlist li[index='+nowindex+']').removeClass().addClass('playing');
			sessionStorage.setItem('playlist',JSON.stringify(playlist));
		});
	});
	
	//  加载新列表到content
	var refreshlist = function(page){
		var i=0;
		if(odata.length<11){
			addNone('one');
		}
		else if(page==1)
		{
			addNone('first');
		}
		else if(page==Math.ceil(odata.length/10))
		{
			addNone('last');
		}
		else
		{
			addNone('111');
		}
		for(var a=(page*10)-10;a<(page*10);a++)
		{
			if(a>=odata.length)
			{
				$(".albumImg:eq("+i+")").attr('src','').css('opacity','0');
				$(".infoUrl:eq("+i+")").attr({'href':'','title':''}).text('');
				$(".singer:eq("+i+")").text('');
				$(".albumName:eq("+i+")").attr('title','').text('');
				$(".playbtn:eq("+i+")").css('display','none');
				$(".addbtn:eq("+i+")").css('display','none');
			}
			else
			{
				$(".albumImg:eq("+i+")").attr('src',odata[a].albumImg).css('opacity','');
				$(".infoUrl:eq("+i+")").attr({'href':odata[a].infoUrl,'title':decode(odata[a].songName)}).text(textflow(decode(odata[a].songName)));
				$(".singer:eq("+i+")").text(textflow(decode(odata[a].singer)));
				$(".albumName:eq("+i+")").attr('title',decode(odata[a].albumName)).text(textflow(decode(odata[a].albumName)));
				$(".playbtn:eq("+i+")").attr('index',a).css('display','inline-block');
				$(".addbtn:eq("+i+")").attr('index',a).css('display','inline-block');
			}
			i++;
			if(i>9) break;
		}
	}
	
	//翻页按钮样式设置
	var addNone = function(str){
		if(str=='first')
		{
			$("#firstpage").removeClass().addClass('none');
			$("#previouspage").removeClass().addClass('none');
			$("#nextpage").removeClass().addClass('pagebtn');
			$("#lastpage").removeClass().addClass('pagebtn');
		}
		else if(str=='last')
		{
			$("#firstpage").removeClass().addClass('pagebtn');
			$("#previouspage").removeClass().addClass('pagebtn');
			$("#nextpage").removeClass().addClass('none');
			$("#lastpage").removeClass().addClass('none');
		}
		else if(str=='one')
		{
			$("#firstpage").removeClass().addClass('none');
			$("#previouspage").removeClass().addClass('none');
			$("#nextpage").removeClass().addClass('none');
			$("#lastpage").removeClass().addClass('none');
		}
		else 
		{
			$("#firstpage").removeClass().addClass('pagebtn');
			$("#previouspage").removeClass().addClass('pagebtn');
			$("#nextpage").removeClass().addClass('pagebtn');
			$("#lastpage").removeClass().addClass('pagebtn');
		}
	}
	
	//首页按钮点击
	$("#firstpage").on('click',function(){
		if($(this).hasClass('none')){return;}
		page=1;
		refreshlist(page);
	});
	
	//上页按钮点击
	$("#previouspage").on('click',function(){
		if($(this).hasClass('none')){return;}
		page=page-1;
		refreshlist(page);
	});
	
	//下页按钮点击
	$("#nextpage").on('click',function(){
		if($(this).hasClass('none')){return;}
		page=page+1;
		refreshlist(page);
	});
	
	//尾页按钮点击
	$("#lastpage").on('click',function(){
		if($(this).hasClass('none')){return;}
		page=Math.ceil(odata.length/10);
		refreshlist(page);
	});
	
	//音乐搜索
	var MusicSearch = function(){
		var keyword = $("input[type=search]").val();
		if(keyword =='' || null){alert('请输入需要搜索的内容！');return;}

		if(sessionStorage.getItem(keyword)!=null)  //本地有搜索数据直接使用
		{
				page=1;
				init=false;
				odata=JSON.parse(sessionStorage.getItem(keyword));
				refreshlist(page);
		}
		else  //本地无搜索数据调用api搜索
		{
			$.getJSON("music/MusicSearch?keyword="+encodeURI(keyword),[],function(data){
				if(data.status=="success" && data.data!=null)
				{
					page=1;
					init=false;
					odata = data.data;
					sessionStorage.setItem(keyword,JSON.stringify(odata));
					refreshlist(page);
				}
				else
				{
					alert('搜索失败！未知错误！请联系我们！');
				}
			}
			);
		}
	}
	//搜索按钮点击
	$(".searchbtn").on('click',function(){
		MusicSearch()
	});
	
	$("input[type=search]").on('keydown',function(){
		if(event.keyCode == "13") {
			MusicSearch();
		}
	});

	if(sessionStorage.commend!=null) //本地存储推荐直接使用
	{
		odata = commend =JSON.parse(sessionStorage.commend);
		createPlaylist(odata);
		loadMusic(0);
	}
	else  //本地未存储推荐ajax获取推荐
	{
		//ajax请求action获取推荐歌曲列表
		$.getJSON("music/MusicCommend",[],function(data){
			if(data.status=="success"  && data.data!=null)
			{
				odata=data.data;
				createPlaylist(odata);
				loadMusic(0);
				sessionStorage.setItem('commend',JSON.stringify(odata));
			}
			else
			{
				alert('推荐列表加载失败！请联系我们！');
			}
			return odata;
		}
		);
	}

})(jQuery);