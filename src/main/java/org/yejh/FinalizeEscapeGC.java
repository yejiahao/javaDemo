package org.yejh;

public class FinalizeEscapeGC {
    private static FinalizeEscapeGC SAVE_HOOK = null;

    private static void isAlive() {
        String result = SAVE_HOOK == null ? "no,i am dead:(" : "yes,i am still alive:)";
        System.out.println(result);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();
        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        FinalizeEscapeGC.isAlive();
        // 下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        FinalizeEscapeGC.isAlive();
    }
}

