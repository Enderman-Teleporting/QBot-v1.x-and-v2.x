# QBot

<small>使用mirai+OverFlow(本质上是OneBot)框架制作的QQ机器人</small>

## 外部项目

我们使用了[免费ChatGPT API](https://github.com/popjane/free_chatgpt_api)来进行消息的回复（感谢@popjane，若群聊较多，并行数较大，请使用付费API，详见该项目)。

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

>如果你需要ChatGPT功能，请前往[免费ChatGPT API](https://github.com/popjane/free_chatgpt_api)获取APIKEY并填入，公益不易，希望各位能够赞助该项目

5. 重启NapCat，如有必要请重新登录

6. 确保NapCat已经完全启动，运行run.bat
7. 完成！在Java窗口中可以输入help进行命令查询，可按提示输入指令