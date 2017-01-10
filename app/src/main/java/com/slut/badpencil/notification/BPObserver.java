package com.slut.badpencil.notification;

/**
 * Created by shijianan on 2017/1/10.
 */

public interface BPObserver {

    void itemInserted(Object obj);

    void itemChanged(Object obj);

    void itemRemoved(Object obj);

}
