# QBot

<small>使用mirai+OverFlow(本质上是OneBot)框架制作的QQ机器人</small>

## 外部项目

我们使用了[CDial-GPT项目](https://github.com/thu-coai/CDial-GPT)来进行消息的回复。

## 主程序语言和其他库

（CDial-GPT所需库省略)

* Java, Kotlin
* OverFlow
* Mirai
* json-lib
* ETToolset(本人自己写的飞舞库)
* Minestat

## 如何使用？

1. 下载最新版本`Release`，并解压，在命令行中cd至该文件夹
2. 下载LLOneBot或Napcat并进行配置，详细方式请见它们的文档
>Release中将会提供LLOneBot和LightLoaderQQNT的安装exe，双击下载即可，你将会在你自己的QQ（如果你在Windows上，用管理员权限运行）设置中看到LLOneBot插件
3. 启用正向WebSocket服务并记录下你的正向WebSocket监听端口(默认为3001)
4. 配置`botInfo.properties`，按照注释的提示进行配置（例如在`host=`后面填入`host=ws://127.0.0.1:+你上步获得的端口号`）
5. 输入`pip install -r requirements.txt`以下载Python依赖
6. 下载完成后运行run.bat，会弹出两个窗口
7. 完成！在Java窗口中可以输入help进行命令查询，可按提示输入指令