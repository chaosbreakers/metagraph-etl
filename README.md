# 特性

1. 支持多种 `Reader`
    - 文件类型（SQL 、 SCV 、 JSON 、 XML 、 JSON）
    - JDBC Driver（Mysql 、 SQLServer 、 Oracle）
2. 支持多种 `Writer`
    - 支持 `Graph` `RemoteGraph` 直接写入
    - 支持 `Rest` `Websocket` 写入
3. 灵活配置
    - 配置点、边写入规则
    - 配置 `Reader` 与 `Writer`
4. 支持定时任务
5. 自动生成模型对应文档，提供简单查询例子
6. 度量统计写入

# 规则

## ID 规则

**Vertex**

对于Vertex ID 走 Graph ID系统，同时生成一个业务 ID ( `bid` )。 `bid` 来至于导入源，由业务系统的 ID 或者 唯一索引定制。

**Edge**

走Graph ID系统

**Property**

走Graph ID系统

## Label 规则

> 注意：需要考虑不同数据集的情况

对于 Vertex Label 可以由客户端自行配置，在 JDBC Driver 读取器的情况下 可以由表名决定。

## Edge 规则

无

## Property 规则

支持配置映射，或直接采用文件、数据库描述

## 写入判断规则

对于任意数据写入，均可以配置写入前置判断 `Traversal` , EG :

```groovy
g.V().hasLabel(label).has('bid',bid)
```

# 类图

如图
![class](http://t.cn/RauSGFK)

# 技术栈

1. Gremlin
2. vert.x-file     (文件读取解析)
3. vert.x-jdbc     (读取 数据库)
4. vert.x-shell    (交互式命令行)
5. vert.x-config   (存储规则)
6. vert.x-schedule (定时任务，用于增量导入)
