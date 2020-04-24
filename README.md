1. #### 生成Apk及Test Apk
-----
![生成应用](https://github.com/272664150/QJunitFramework/blob/master/screenshots/1.png)

2. #### 执行全部TestCase
-----
![执行用例](https://github.com/272664150/QJunitFramework/blob/master/screenshots/2.png)

3. #### 在SDCard生成XML测试报告
-----
![生成报告](https://github.com/272664150/QJunitFramework/blob/master/screenshots/3.jpg)

4. #### XML转HTML格式测试报告
-----
##### 	1) 配置Python环境

​    		只能在 v3.8.x 以下的环境正常执行

##### 	2) 安装junit2html拓展包

```python
$ sudo pip install junit2html
```

##### 	3) 执行转换操作

​	*a. 单文件转换*

```python
Usage:
	$ junit2html JUNIT_XML_FILE [NEW_HTML_FILE]
eg:
	$ junit2html pytest-results.xml testrun.html
or
	$ python -m junit2htmlreport pytest-results.xml
```

![单文件转换](https://github.com/272664150/QJunitFramework/blob/master/screenshots/4.png)
​	*b. 批量转换*

```python
$ python batchprocessing_junit2htmlreport.py XML报告根目录
```

![批量转换](https://github.com/272664150/QJunitFramework/blob/master/screenshots/5.png)

#####     	4) HTML测试报告

![HTML测试报告](https://github.com/272664150/QJunitFramework/blob/master/screenshots/6.png)