package com.slut.badpencil.notification.subject;

import com.slut.badpencil.notification.BPObserver;
import com.slut.badpencil.notification.BPSubject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shijianan on 2017/1/10.
 */

public class PasswordSubject implements BPSubject{

    private static volatile PasswordSubject instances = null;
    private List<BPObserver> bpObserverList = new ArrayList<>();

    public static PasswordSubject getInstances() {
        if(instances == null){
            synchronized (PasswordSubject.class){
                if(instances == null){
                    instances = new PasswordSubject();
                }
            }
        }
        return instances;
    }

    @Override
    public void registerObserver(BPObserver bpObserver) {
        bpObserverList.add(bpObserver);
    }

    @Override
    public void removeObserver(BPObserver bpObserver) {
        bpObserverList.remove(bpObserver);
    }

    @Override
    public void notifyItemInserted(Object obj) {
        for (BPObserver bpObserver:bpObserverList){
            bpObserver.itemInserted(obj);
        }
    }

    @Override
    public void notifyItemChanged(Object obj) {
        for (BPObserver bpObserver:bpObserverList){
            bpObserver.itemChanged(obj);
        }
    }

    @Override
    public void notifyItemRemoved(Object obj) {
        for (BPObserver bpObserver:bpObserverList){
            bpObserver.itemRemoved(obj);
        }
    }


}
