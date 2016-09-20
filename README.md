# june-notebook-1.0v
========================
#### JUNE1.0v版本（原蓝缘系统开源免费版）

> 严格执行开源大业！奋斗吧，少年
> 总的来说，还得要感谢*蓝缘*大人的无私的奉献精神，把最初的东西__开源__了。
> 抛砖引玉这种活儿，我是做不来的.
> 我顶多就是做一些**锦上添花**的事情...

### 进度
> 1. 160902 添加国际化支持
> 2. 160901 修改若干bug,增强系统稳定性
> 3. 160906 整理邮件发送，密码应该设置为注册邮箱后，设置的授权码，而不是登陆邮箱的密码，否则验证不通过
> 4. 160906 新增MessageUtil工具类，方便读取.properties资源文件
> 5. 160906 格式化代码，完善messages_zh.properties文件

### 下一步计划
> 1. 添加国际化支持 在java代码中发现大量的中文字符，看着不舒服.
> 2. 回归原生的mybitas，尽量减少对mybitas源码的修改;
> 3. 增加系统功能 权限管理可能需要重构.

=============================

# JUNE开源系统介绍
接管开源大业，任重而道远也 by june @ 2016-09-01
> 1. 听说原系统要修费了，收费就收费吧，我们接着搞起...
> 2. 版本1.0v,源码在[这里](https://github.com/junehappylove/june_BMS)

## 关于1.0新版本的说明：

### 一大亮点：
* 采用最新的技术流行框架：springMVC4.1.4+shiro1.2.3+spring4.x+Mybaits3.2.8+Ajax+html5
* spring4.x的新特性请看:[这里](http://jinnianshilongnian.iteye.com/blog/1989381).

#### 说明：
* 这个版本主要是对原有的JUNE系统更换UI界面,功能上基本一致, 
* 但此还在开发当中..... 关于以前版本,不再维护,致力于新版本的开发和维护..

#### 优化：
* 封装好baseSerive,baseSeriveImpl,baseMapper..服务层，持久层统一调用,大大减少代码开发时间.
* spring4.x的强类型注解，泛型限定式依赖注入.
* 用mapper来代替dao,由mybaits自动管理各事务的操作,大大减少代码开发时间.
* 3.0版本不再使用spring Security3权限安全机制,采用了shiro权限机制.
* 为何愿意使用Apache Shiro ?请看:[这里](http://www.infoq.com/cn/articles/apache-shiro)

### 技术要点：
> 1. 此版本采用ajax+js分页,表格lyGrid分页插件是群主自己写的,有点模仿ligerui的分页实现.
> 2. 列表的表头固定,兼容IE,firefox,google,360的浏览器,其他暂没有测试.
> 3. 表格排序功能.
> 4. 弹窗采用贤心的插件，请看:[这里](http://sentsin.com/jquery/layer/)
> 5. 加入druid技术,对sql语句的监控.
> 6. 自定义注解导出excel.
> 7. 使用了ehcache缓存机制.
> 8. 新增支持oracle分页实现.
> 9. 新增支持SQLserver2008分页实现.
> 10. 解决分页参数没法传到后台的问题.
> 11. 异常统一处理.
> 12. 使用Spring Security3权限安全机制,采用了shiro权限机制.
> 13. 封装好baseSerive,baseSeriveImpl,baseMapper..服务层，持久层统一调用.
> 14. ........

