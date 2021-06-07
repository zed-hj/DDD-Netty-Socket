package com.zed.application.in.event;

/**
 *  指一件已经发生过的既有事实，需要系统根据这个事实作出改变或者响应的，通常事件处理都会有一定的写操作。事件处理器不会有返回值。
 *  这里需要注意一下的是，Application层的Event概念和Domain层的DomainEvent是类似的概念，但不一定是同一回事，
 *  这里的Event更多是"外部"一种通知机制而已。
 */