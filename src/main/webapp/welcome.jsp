<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="col-md-12">
	<div class="alert alert-warning alert-block">
		<table>
			<tr>
				<td align="center" colspan="2"><h1>JUNE开源系统</h1></td>
			</tr>
			<tr>
				<td><div class="topBtn" style="font-size: 15px;">
						<h3>接管开源大业，任重而道远也 --june @ 2016-09-01</h3>
						<div style="padding-left: 20px; color: red;">
							1.听说原系统要修费了，收费就收费吧，我们接着搞起...<br>
							2.版本1.0v：<a href="https://github.com/junehappylove/june_BMS" title="源码这里有" target="_blank">https://github.com/junehappylove/june_BMS</a><br>
						</div>
						<br> 关于1.0新版本的说明：
						<div style="padding-left: 20px;">
							一大亮点：
							<div style="padding-left: 20px; color: red;">
								采用最新的技术流行框架：springMVC4.1.4+shiro1.2.3+spring4.x+Mybaits3.2.8+Ajax+html5<br>
							</div>
							spring4.x的新特性请看：
							<div style="padding-left: 20px; color: blue;">
								http://jinnianshilongnian.iteye.com/blog/1989381</div>
						</div>
						<div style="padding-left: 20px;">
							说明：
							<div style="padding-left: 20px;">
								这个版本主要是对原有的JUNE系统更换UI界面,功能上基本一致, 但此还在开发当中.....
								关于以前版本,不再维护,致力于新版本的开发和维护..</div>
							优化：
							<div style="padding-left: 20px; color: blue; padding-top: 10px;">
								封装好baseSerive,baseSeriveImpl,baseMapper..服务层，持久层统一调用,大大减少代码开发时间.
							</div>
							<div style="padding-left: 20px; color: blue; padding-top: 10px;">
								spring4.x的强类型注解，泛型限定式依赖注入</div>
							<div style="padding-left: 20px; padding-top: 10px;">
								用mapper来代替dao,由mybaits自动管理各事务的操作,大大减少代码开发时间.</div>
							<div style="padding-left: 20px; color: red; padding-top: 10px;">
								3.0版本不再使用spring Security3权限安全机制,采用了shiro权限机制, 为何愿意使用Apache
								Shiro ？请看：http://www.infoq.com/cn/articles/apache-shiro</div>
							技术要点：
							<div style="padding-left: 20px;">
								1：此版本采用ajax+js分页,表格lyGrid分页插件是群主自己写的,有点模仿ligerui的分页实现 <br>
								2：列表的表头固定,兼容IE,firefox,google,360的浏览器,其他暂没有测试.<br>
								3：表格排序功能<br>
								4：弹窗采用贤心的插件，网址：http://sentsin.com/jquery/layer/<br>
								5：加入druid技术,对sql语句的监控.<br> 
								6：自定义注解导出excel<br> 
								7：<font color="blue">使用了ehcache缓存机制</font><br> 
								8：<font color="blue">新增支持oracle分页实现</font><br> 
								9：<font color="blue">新增支持SQLserver2008分页实现</font><br> 
								10：<font color="blue">解决分页参数没法传到后台的问题</font><br> 
								11：<font color="blue">异常统一处理</font><br> 
								12：<font color="blue">使用Spring Security3权限安全机制,采用了shiro权限机制</font><br> 
								13：	<font color="blue">封装好baseSerive,baseSeriveImpl,baseMapper..服务层，持久层统一调用</font><br>
								14：........<br>
							</div>
						</div>
					</div>
				</td>
				<td style="width: 290px;">
					<div style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; WIDTH: 100%; HEIGHT: 148px; border: 1px solid #cacaca; background: #FFFFFF">
						<div style="WIDTH: 100%; clear: both; height: 31px; background-image: url(http://www.tianqi.com/static/images/code/bg_13.jpg); background-repeat: repeat-x; border-bottom: 1px solid #cacaca;">
							<div style="float: left; height: 31px; color: #9e0905; font-weight: bold; line-height: 31px; margin-left: 20px; font-size: 14px;">城市天气预报</div>
						</div>
						<iframe width="400" scrolling="no" height="120" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=19&bgc=%23FFFFFF&bdc=%23&icon=1&temp=1&num=2"></iframe>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="col-md-6">
	<div class="alert alert-danger">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<div style="font-size: 17px;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			JUNE有一个自己的网站空间,所有在学习和使用JUNE系统的同志们捐助1元或2元就行(要求不过分吧!街边吃顿都10元以上啊.....),
			以下是JUNE系统捐助的支付页面，由于空间网站需求收费，故此征集大家为JUNE系统做一点贡献，有了你的支持，JUNE系统将做得更好，服务开源，献身开源！再次致谢
			！</div>
		<img alt="JUNE系统的支付连接"
			src="${pageContext.request.contextPath}/images/zhifubao.png"
			title="JUNE系统的支付连接"> <span title="JUNE系统的支付连接"
			style="font-size: 20px; color: blue;">支付宝账号是:wjw.happy.love@163.com</span><br>
		<span title="JUNE系统的支付连接" style="font-size: 20px; color: blue;">支付地址:
			支付地址已经关闭,亲可以直接转到支付宝账号上，记得备注哦！</span>
	</div>
</div>
<div class="col-md-6">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<table style="width: 100%;">
			<tr>
				<td style="font-size: 17px; color: blue; width: 150px;" valign="top">感谢捐助:</td>
				<td align="left" style="font-size: 14px; color: blue;">
					以下是JUNE系统捐助的支付页面，由于空间网站需求收费，故此征集大家为JUNE系统做一点贡献，有了你的支持，JUNE系统将做得更好，服务开源，献身开源！再次致谢
					！</td>
			</tr>
			<tr>
				<td>支付宝账号是:</td>
				<td style="font-size: 17px; color: blue;">wjw.happy.love@163.com</td>
			</tr>
			<tr>
				<td width="116"><img alt="JUNE系统的支付连接" src="${pageContext.request.contextPath}/images/zhifubao.png"></td>
				<td><span title="JUNE系统的支付连接" style="font-size: 17px; color: blue;">支付地址已经关闭,亲可以直接转到支付宝账号上，
				<span style="font-size: 17px; color: red;">捐助请备注哦！谢谢！</span></span></td>
			</tr>
			<tr>
				<td>技术支持:</td>
				<td style="font-size: 17px; color: blue;">QQ:980154978</td>
			</tr>
		</table>
	</div>
</div>
<div class="col-md-12">
	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<div style="font-size: 14px; text-align: left; padding: 5px;">
			<span style="font-weight: 800; font-size: 18px;">感谢捐助者:&nbsp;&nbsp;&nbsp;<span
				style="font-size: 12px;">(排名不分先后.可能有些忘记了写上,可以联系我,我补上,非常感谢各位)</span></span><br>
		</div>
	</div>
</div>
