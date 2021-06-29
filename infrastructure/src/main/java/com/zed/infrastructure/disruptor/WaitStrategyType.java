package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 等待策略枚举
 *
 * @author zed
 **/
@Getter
@AllArgsConstructor
public enum WaitStrategyType {
    /**
     * 加锁	CPU资源紧缺，吞吐量和延迟并不重要的场景
     */
    BLOCKING(new BlockingWaitStrategy()),
    /**
     * 自旋	通过不断重试，减少切换线程导致的系统调用，而降低延迟。推荐在线程绑定到固定的CPU的场景下使用
     */
    BUSY_SPIN(new BusySpinWaitStrategy()),
    /**
     * 自旋 + yield + 自定义策略	CPU资源紧缺，吞吐量和延迟并不重要的场景
     */
//    PBO(new PhasedBackoffWaitStrategy()),
    /**
     * 自旋 + yield + sleep	性能和CPU资源之间有很好的折中。延迟不均匀
     */
    SLEEPING(new SleepingWaitStrategy()),
    /**
     * 加锁，有超时限制	CPU资源紧缺，吞吐量和延迟并不重要的场景
     */
    TIMEOUT_BLOCKING(new TimeoutBlockingWaitStrategy(100, TimeUnit.MICROSECONDS)),
    /**
     * 自旋 + yield + 自旋	性能和CPU资源之间有很好的折中。延迟比较均匀
     */
    YIELDING(new YieldingWaitStrategy());

    private WaitStrategy waitStrategy;

}
