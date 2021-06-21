package com.zed.domain.aggregate.entity.valueobj;

import com.zed.domain.config.SeverChannelInitializerConfig;
import com.zed.domain.config.SocketConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zed
 */
public class Server {

    private SocketConfig socketConfig;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    public Server(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

    protected void initGroups() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(1);
    }

    protected void stopGroups() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void run() throws Exception {
        // 初始化
        initGroups();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SeverChannelInitializerConfig(this.socketConfig));
            applyConnectionOptions(b);

            ChannelFuture f = b.bind(socketConfig.getPort()).sync();

            f.channel().closeFuture().sync();
        } finally {
            stopGroups();
        }
    }

    /**
     * 设置Boos 或者 Worker 的参数配置
     *
     * @param bootstrap
     */
    public void applyConnectionOptions(ServerBootstrap bootstrap) {
        /**
         * Nagle算法是将小的数据包组装为更大的帧然后进行发送，而不是输入一次发送一次,因此在数据包不足的时候会等待其他数据的到了，
         * 组装成大的数据包进行发送，虽然该方式有效提高网络的有效负载，但是却造成了延时，而该参数的作用就是禁止使用Nagle算法，
         * 使用于小数据即时传输，于TCP_NODELAY相对应的是TCP_CORK，该选项是需要等到发送的数据量最大的时候，一次性发送数据，适用于文件传输
         */
        bootstrap.childOption(ChannelOption.TCP_NODELAY, socketConfig.isTcpNoDelay());

        if (socketConfig.getTcpSendBufferSize() != -1) {
            /**
             * ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，
             * ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，
             * 接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功
             */
            bootstrap.childOption(ChannelOption.SO_SNDBUF, socketConfig.getTcpSendBufferSize());
        }
        if (socketConfig.getTcpReceiveBufferSize() != -1) {
            /**
             * ChannelOption.SO_SNDBUF参数对应于套接字选项中的SO_SNDBUF，
             * ChannelOption.SO_RCVBUF参数对应于套接字选项中的SO_RCVBUF这两个参数用于操作接收缓冲区和发送缓冲区的大小，
             * 接收缓冲区用于保存网络协议站内收到的数据，直到应用程序读取成功，发送缓冲区用于保存发送数据，直到发送成功
             */
            bootstrap.childOption(ChannelOption.SO_RCVBUF, socketConfig.getTcpReceiveBufferSize());

            bootstrap.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(socketConfig.getTcpReceiveBufferSize()));
        }

        /**
         * 套接字选项中的SO_KEEPALIVE，该参数用于设置TCP连接，当设置该选项以后，连接会测试链接的状态，
         * 这个选项用于可能长时间没有数据交流的连接。
         * 当设置该选项以后，如果在两小时内没有数据的通信时，TCP会自动发送一个活动探测数据报文。
         */
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, socketConfig.isTcpKeepAlive());
        /**
         * Linux内核默认的处理方式是当用户调用close()方法的时候，函数返回，在可能的情况下，尽量发送数据，
         * 不一定保证会发生剩余的数据，造成了数据的不确定性，使用SO_LINGER可以阻塞close()的调用时间，直到数据完全发送
         */
        bootstrap.childOption(ChannelOption.SO_LINGER, socketConfig.getSoLinger());

        /**
         * 这个参数表示允许重复使用本地地址和端口，比如，某个服务器进程占用了TCP的80端口进行监听，此时再次监听该端口就会返回错误，
         * 使用该参数就可以解决问题，该参数允许共用该端口，这个在服务器程序中比较常使用，比如某个进程非正常退出，
         * 该程序占用的端口可能要被占用一段时间才能允许其他进程使用，而且程序死掉以后，内核一需要一定的时间才能够释放此端口，
         * 不设置SO_REUSEADDR就无法正常使用该端口。
         */
        bootstrap.option(ChannelOption.SO_REUSEADDR, socketConfig.isReuseAddress());

        /**
         * 对应的是tcp/ip协议listen函数中的backlog参数，函数listen(int socketfd,int backlog)用来初始化服务端可连接队列，
         * 服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
         * 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小
         */
        bootstrap.option(ChannelOption.SO_BACKLOG, socketConfig.getAcceptBackLog());
    }

}
