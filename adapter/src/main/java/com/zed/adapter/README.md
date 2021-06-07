### Interface接口层（Adapter）

随着REST和MVC架构的普及，经常能看到开发同学直接在Controller中写业务逻辑，如上面的典型案例，但实际上MVC Controller不是唯一的重灾区。以下的几种常见的代码写法通常都可能包含了同样的问题：

- HTTP 框架：如Spring MVC框架，Spring Cloud等
- RPC 框架：如Dubbo、HSF、gRPC等
- 消息队列MQ的“消费者”：比如JMS的 onMessage，RocketMQ的MessageListener等
- Socket通信：Socket通信的receive、WebSocket的onMessage等
- 文件系统：WatcherService等
- 分布式任务调度：SchedulerX等

这些的方法都有一个共同的点就是都有自己的网络协议，而如果我们的业务代码和网络协议混杂在一起，则会直接导致代码跟网络协议绑定，无法被复用。
所以，在DDD的分层架构中，我们单独会抽取出来Interface接口层，作为所有对外的门户，将网络协议和业务逻辑解耦。


### 接口层主要由以下几个功能组成
- 网络协议的转化：通常这个已经由各种框架给封装掉了，我们需要构建的类要么是被注解的bean，要么是继承了某个接口的bean。
- 统一鉴权：比如在一些需要AppKey+Secret的场景，需要针对某个租户做鉴权的，包括一些加密串的校验
-  Session管理：一般在面向用户的接口或者有登陆态的，通过Session或者RPC上下文可以拿到当前调用的用户，以便传递给下游服务。
-  限流配置：对接口做限流避免大流量打到下游服务
-  前置缓存：针对变更不是很频繁的只读场景，可以前置结果缓存到接口层
-  异常处理：通常在接口层要避免将异常直接暴露给调用端，所以需要在接口层做统一的异常捕获，转化为调用端可以理解的数据格式
-  日志：在接口层打调用日志，用来做统计和debug等。一般微服务框架可能都直接包含了这些功能。
  
当然，如果有一个独立的网关设施/应用，则可以抽离出鉴权、Session、限流、日志等逻辑，但是目前来看API网关也只能解决一部分的功能，即使在有API网关的场景下，应用里独立的接口层还是有必要的。
在interface层，鉴权、Session、限流、缓存、日志等都比较直接，只有一个异常处理的点需要重点说下。


注：这部分主要还是面向REST和RPC接口，其他的协议需要根据协议的规范产生返回值。
在我见过的一些代码里，接口的返回值比较多样化，有些直接返回DTO甚至DO，另一些返回Result。
接口层的核心价值是对外，所以如果只是返回DTO或DO会不可避免的面临异常和错误栈泄漏到使用方的情况，包括错误栈被序列化反序列化的消耗。所以，这里提出一个规范：

规范：Interface层的HTTP和RPC接口，返回值为Result，捕捉所有异常


规范：Application层的所有接口返回值为DTO，不负责处理异常

Application层的具体规范等下再讲，在这里先展示Interface层的逻辑。

@PostMapping("checkout")
public Result<OrderDTO> checkout(Long itemId, Integer quantity) {
  	try {
	      CheckoutCommand cmd = new CheckoutCommand();
				OrderDTO orderDTO = checkoutService.checkout(cmd);    
				return Result.success(orderDTO);
	  } catch (ConstraintViolationException cve) {
        // 捕捉一些特殊异常，比如Validation异常
      	return Result.fail(cve.getMessage());
    } catch (Exception e) {
        // 兜底异常捕获
  		  return Result.fail(e.getMessage());
  	}
}
