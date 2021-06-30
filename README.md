# DDD-Netty-Socket


DDD实现的websocket


![avatar](消息走向图.png)

- adapter 适配器 主要用于接收外部命令
- application 服务编排
- infrastructure ACL防腐层实现、从不同的数据源加载数据、仓库等
- domain 领域层 主要有领域服务、聚合根