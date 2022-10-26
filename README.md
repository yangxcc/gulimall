# gulimall 尚硅谷-谷粒商城

问题记录

- 配置人人开源后台管理系统时，出现了验证码刷新不出的问题，这里的解决方案是在前端项目`renren-fast-vue`开启代理
    -  `conf/dev.env.js`中修改 OPEN_PROXY 为 true
    - `conf/index.js`中设置代理API target: 'http://localhost:8081/renren-fast/' 

