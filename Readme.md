# 辅助工具项目

## 汇率换算
http://domain.com/er/convert?amount=100&from=USD&to=CNY
from,to的代码，[参看：ISO-4217](http://www.iso.org/iso/home/standards/currency_codes.htm)

## 翻译引擎
以POST方式将数据提交到http://domain/trans，格式为：
'''
{
"content":"可以。詹姆斯依然是NBA最好的球员，不过在克里夫兰有点难",
"from":{"language":"zh", "country":"CN"},
"to":{"language":"ja","country":"jp"}
}
'''
结果：
```
{"from":{"language":"zh","country":"CN","locale":"zh_CN"},"to":{"language":"en","country":"us","locale":"en_US"},"translateResult":"Sure。 James is still the best player in NBA, but it's a little hard at Cleveland","source":"可以。詹姆斯依然是NBA最好的球员，不过在克里夫兰有点难"}
```