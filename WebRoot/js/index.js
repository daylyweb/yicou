		var page=1;
		var odata;
		var ws;
		
		$(document).ready(function(){ //文档加载事件
			getJson();
			bindEvent();
		});
		
		
		function bindEvent()   //绑定各种事件
		{
			var btnflag=true;
			$("#shopselect").click(function(){
				$(".shopselectoption").css("display","block");
				$(".jiantou:first").attr("src","images/index/up.png");
			});  //商城点击
			
			$("#areaselect").click(function(){
				$(".areaselectoption").css("display","block");
				$(".jiantou:last").attr("src","images/index/up.png");
			});  //地区点击
			
			$(".shopselectoption").mouseleave(function()
			{
				$(".shopselectoption").css("display","none");
				$(".jiantou:first").attr("src","images/index/down.png");
			
			});  //鼠标离开
			
			$(".areaselectoption").mouseleave(function()
			{
				$(".areaselectoption").css("display","none");
				$(".jiantou:last").attr("src","images/index/down.png");
			}); //鼠标离开
		
			$(".shopoption").click(function()
			{
				$(".selected:first span:first-child").text($(this).html().replace("&nbsp;",""));
				$("input[name='shop']").val($(this).attr("value"));
				$(".shopselectoption").css("display","none");
			});  //商城选中
			
			$(".areaoption").click(function()
			{
				$(".selected:last span:first-child").text($(this).html().replace("&nbsp;",""));
				$("input[name='area']").val($(this).attr("value"));
				$(".areaselectoption").css("display","none");
			});	 //地区选中
			
			$(".cou").click(function(){
				$("#form").submit();
			});  //凑一下按钮
			
			$("#topbtn").click(function(){
				//window.location.href="#top";
				$(window).scrollTop(0);
			});  //top按钮点击事件
			
			$("#upbtn").click(function(){
				$(window).scrollTop($(window).scrollTop()-$(window).height());
			});  //上一页按钮点击事件
			
			$("#downbtn").click(function(){
				$(window).scrollTop($(window).scrollTop()+$(window).height());
			});  //下一页按钮点击事件
			
			$("#stoptn").click(function(){
				if(btnflag==true)
				{
					$("#stoptn img").attr("src","images/index/start.png");
					$("#stoptn").attr("title","开启自动加载商品");
					window.onscroll=function()
					{
						//添加滚动条滚动事件
						ws=$(window).scrollTop();
						guideslide(); //右侧随滚动条滚动
					};
					btnflag=false;
				}
				else
				{
					$("#stoptn img").attr("src","images/index/stop.png");
					$("#stoptn").attr("title","关闭自动加载商品");
					window.onscroll=function()
					{
						//添加滚动条滚动事件
						ws=$(window).scrollTop();
						loading();  //滚动加载商品
						guideslide(); //右侧随滚动条滚动
					};
					btnflag=true;
				}
 			});  //停止按钮点击事件
		}
		
		
		function getJson() //调用api获取商品
		{
			var shop = $("input[name='shop']").val();
			var area = $("input[name='area']").val();
			$.getJSON("api/return?shop="+shop+"&area="+area,[],function(data){
			odata=data;
			//appenddata();  
			if(odata.status=="success" && odata.data.length!=0)
			{
				window.onscroll=function()
				{
					//添加滚动条滚动事件
					ws=$(window).scrollTop();
					loading();  //滚动加载商品
					guideslide(); //右侧随滚动条滚动
				};
			}
			});
		}
		
		function loading() //加载商品
		{
			var dh=$(document).height();
			var wh=$(window).height();
			if(dh<ws+wh+100)
			{
				appenddata();
			}
			
		}
		
		function appenddata()  //添加商品到content-content
		{
			page++;
			for(var i=page*15-15;i<page*15;i++)
			{
				if(i>=odata.data.length){return;}
				var goodsdiv=$("<div>",{class:"goodsdiv"});
				var pic = $("<div>",{class:"pic"});
				var pic_a = $("<a>",{href:odata.data[i].url,target:"_blank",title:odata.data[i].name});
				var img = $("<img>",{src:odata.data[i].imgurl,title:odata.data[i].name});
				var namediv=$("<div>",{class:"name"});
				var name_a = $("<a>",{href:odata.data[i].url,target:"_blank",title:odata.data[i].name});
				var salesdiv=$("<div>",{class:"sales"});
				salesdiv.append("<span>已售"+odata.data[i].sales+"件</span>");
				var pricediv=$("<div>",{class:"price"});
				pricediv.append("<span>￥"+odata.data[i].price+"</span>");
				pic_a.appendTo(pic);
				img.appendTo(pic_a);
				name_a.html(odata.data[i].name);
				name_a.appendTo(namediv);
				pic.appendTo(goodsdiv);
				namediv.appendTo(goodsdiv);
				salesdiv.appendTo(goodsdiv);
				pricediv.appendTo(goodsdiv);
				goodsdiv.appendTo("#content-content");
			}
		}
		
		function guideslide()  //右侧随滚动条滚动
		{
			var wh=$(window).height();
			var rg= $("#right-guide").height();
			var bh = $("#btngroup").height();
			var kh = (wh-rg-bh);
			if(ws>220)
			{
				
				$("#right-guide").css("top",(ws-160)+"px");
				$("#btngroup").css("top",(ws+kh)+"px");
			}
			else
			{
				var wh=$(window).height();
				$("#right-guide").css("top","60px");
				//$("#btngroup").css("top",(ws+rg+kh-60)+"px");
				$("#btngroup").css("top",(rg+90)+"px");
			}
			
		}
		

		function showMianze()
		{
			location.href="#mianzediv";
			var flag=true;
			setInterval(function(){
				if (flag)
				{
					$("#mianzediv").css("background-color","#6699ff");
					flag=false;
				}
				else
				{
					$("#mianzediv").css("background-color","#66ccff");
					flag=true;}
				},700);
		}