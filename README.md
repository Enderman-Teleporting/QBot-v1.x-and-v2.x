# QBot

<small>使用mirai+OverFlow(本质上是OneBot)框架制作的QQ机器人</small>


## 主程序语言和其他库

* Java, Kotlin
* OverFlow
* Mirai
* FastJson
* ETToolset(本人自己写的飞舞库)
* Minestat

## 如何使用？

1. 下载最新版本`Release`，并解压，在命令行中cd至该文件夹
2. 下载`Release`中的两个zip，分别解压
3. 启动NapCat，登录后按[文档](https://napneko.pages.dev/config/basic)进行配置（尽量选择[通过 文件 配置OneBot服务](https://napneko.pages.dev/config/basic#通过-文件-配置onebot服务)，只需填写`websocketServers`部分），记下正向WS的监听主机和监听端口（推荐`127.0.0.1:3001`）
4. 配置`botInfo.properties`，按照注释的提示进行配置（例如在`host=`后面填入`host=ws://127.0.0.1:+你上步获得的端口号`）

>如果你需要ChatGPT功能，你可以选择前往[免费ChatGPT API](https://github.com/popjane/free_chatgpt_api)获取APIKEY并填入，公益不易，希望各位能够赞助该项目

5. 重启NapCat，如有必要请重新登录

6. 确保NapCat已经完全启动，运行run.bat
7. 完成！在Java窗口中可以输入help进行命令查询，可按提示输入指令

## 主要功能

以下功能均可在botInfo.properties中配置,详见该文件
### 奇奇怪怪的事件/请求处理
##### 自动通过好友请求
无自动通过群邀请功能,暂时不能读取链接,你可以登录机器人的账号手动处理
##### 更改群名称
需机器人管理员权限  
群成员可以通过`群名称 [要更改的群名称]`来更改群名称,注意中间有个空格
##### 自助管理
简称一分钟管理体验卡,**_默认关闭_**,需机器人群主权限  
群成员可以通过`我要管理`来获得管理一分钟
对群成员不放心的建议不要开
##### 群名称更改事件
当群名称被更改时,机器人会自动发送一条消息告知群成员
##### 群成员退群事件
当群成员退群时,机器人会自动发送一条消息告知群成员
##### 群龙王更改事件
???真的还有人关心这个吗

### 消息/互动
##### 戳一戳
机器人会自动回复戳一戳
##### 消息回复
需要提供GPT的APIkey以及API的URL,使用的是gpt-4o-mini回复,也有DALL-E2绘图  
绘图语法为`绘图 [提示词(最好英文)]`,注意空格,聊天回复私聊直接聊,群聊需at一下机器人
##### 戳一戳
非常逆天的戳一戳回复
##### 复读
当群内相同消息超过两条会自动复读(+1功能)
##### 查服
这是MC查服,可以查看MC服务器状态  
语法`查服 [ip]:[端口]`或`查服 [ip]`,注意空格,冒号为英文冒号

### 游戏
##### 俄罗斯轮盘
语法`俄罗斯轮盘 [子弹数量]`以发起挑战,注意空格   
如果有人发起了挑战,你可以回复`接受挑战`来接受挑战,若30秒内没人接受,挑战会被取消   
接下来两个人互相说`开枪`,直至一个幸运逝者降临
这具体是个啥请自行百度


### PS

推荐一个[免费ChatGPT API](https://github.com/popjane/free_chatgpt_api)来进行消息的回复（感谢@popjane，若群聊较多，并行数较大，请使用付费API，详见该项目)。