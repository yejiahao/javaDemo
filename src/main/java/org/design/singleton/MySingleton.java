package org.design.singleton;

public class MySingleton {
    private static MySingleton instance_h = new MySingleton();
    private static volatile MySingleton instance;

    private MySingleton() {
        // 防止反射调用
        throw new AssertionError();
    }

    /**
     * 饿汉模式（线程安全）
     */
    public static MySingleton getInstanceHungry() {
        return instance_h;
    }

    /**
     * 懒汉模式（线程不安全）
     */
    public static MySingleton getInstanceLazy() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

    /**
     * 访问加锁
     */
    public synchronized static MySingleton getInstanceLock() {
        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

    /**
     * 双重检查锁
     */
    public static MySingleton getInstanceDbLock() {
        if (instance == null) {
            synchronized (MySingleton.class) {
                if (instance == null) {
                    // volatile 防止指令重排序
                    instance = new MySingleton();
                }
            }
        }
        return instance;
    }

    private static class MySingletonHolder {
        private static MySingleton instance = new MySingleton();
    }

    /**
     * 静态内部类
     */
    public static MySingleton getInstanceHolder() {
        if (instance == null) {
            instance = MySingletonHolder.instance;
        }
        return instance;
    }

    public enum MySingletonEnum {
        INSTANCE;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 枚举类
     */
    public static MySingletonEnum getInstanceEnum() {
        return MySingletonEnum.INSTANCE;
    }
}
