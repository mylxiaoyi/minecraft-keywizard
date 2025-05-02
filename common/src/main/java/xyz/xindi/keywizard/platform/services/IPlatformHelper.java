package xyz.xindi.keywizard.platform.services;

public interface IPlatformHelper {
    /**
     * 获取当前平台名称
     */
    String getPlatformName();

    /**
     * 注册客户端事件
     */
    void registerClientEvents();

    /**
     * 检查是否在客户端环境
     */
    boolean isClientEnvironment();
}