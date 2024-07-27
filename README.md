# QBot

<small>使用mirai框架制作的QQ机器人</small>

## 外部项目

我们使用了[CDial-GPT项目](https://github.com/thu-coai/CDial-GPT)来进行消息的回复。

## 主程序语言和其他库

（CDial-GPT所需库省略)

* Java, Kotlin

* Mirai
* json-lib
* ETToolset(本人自己写的飞舞库)
* Minestat

## 如何使用？

1. 确保你有Java环境（推荐17），以及Python环境（建议3.7到3.8）
2. 下载最新版本`Release`，并解压，在命令行中cd至该文件夹
3. 配置`botInfo.properties`，例如配制自己的QQ号等
4. 输入`pip install -r requirements.txt`以下载Python依赖
5. 下载完成后运行run.bat，会弹出两个窗口
6. 在机器人窗口（Java）中按提示进行扫码登录
7. 完成！