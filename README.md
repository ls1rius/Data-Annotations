# Data-Annotations
数据标注系统
## 主要技术：
前端：
1. annotator插件
2. element-ui
3. vue.js
    
后端：
1. SSM

训练模型：
1. CRF++
2. python
## 界面效果图
![界面效果图](./static/界面效果图.png 
)
## 功能模块图
![功能模块图](./static/功能模块图.png)

## 安装部署指南：

### 前端：
#### poplar-annotation: 
https://github.com/synyi/poplar
安装命令: npm i poplar-annotation
#### axios
安装命令：npm install axios
#### element-ui
安装命令：npm i element-ui -S
#### hightlight.js
安装命令：npm install highlight.js
#### 其他：
vuex、vue-router、vuetify
#### 前端编译软件：
采用的是 VSCode
#### 其他：
npm和node都用最新版本吧，适配就好，自己搭建一下具体开发环境，然后直接拖进去一个src文件就可以

### 后端：
#### jdk 9
#### tomcat 9.0.1
#### 后端编译软件：
采用的是 IntelliJ IDEA
#### 其他：
同前端，自己搭建一下开发环境，直接拖个src进去，记得改数据库密码


## 其他
1. 自动训练处只在前后端预留了接口，具体实现逻辑以及增量训练的数据处理未完成
2. 需要在后端 /SSM/CrfPro/src/main/Java/com/cm/common/FilesMethod.java 处修改文件保存路径，以及模型加载路径
3. 界面内数据均来自于数据库动态加载
4. 代码丑陋，仅供实验室自娱自乐
5. 有事咨询邮箱 865212941@qq.com


## Tip:
如果不会安装VUE或者SSM框架可以参考本人模板
[SSM模板](https://github.com/ls1rius/SSM-DEMO.git)
[VUE模板](https://github.com/ls1rius/VUE-DEMO.git)

