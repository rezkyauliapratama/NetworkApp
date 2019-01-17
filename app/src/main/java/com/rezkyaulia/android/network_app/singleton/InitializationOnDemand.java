package com.rezkyaulia.android.network_app.singleton;

/**
 * Created by Rezky Aulia Pratama on 8/12/2017.
 */

public class InitializationOnDemand {

    private InitializationOnDemand() {}

    private static class LazyHolder {
        static final InitializationOnDemand INSTANCE = new InitializationOnDemand();
    }

    public static InitializationOnDemand getInstance() {
        return LazyHolder.INSTANCE;
    }

    int value = 1 ;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
