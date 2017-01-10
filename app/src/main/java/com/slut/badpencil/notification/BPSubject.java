package com.slut.badpencil.notification;

import java.util.Objects;

/**
 * Created by shijianan on 2017/1/10.
 */

public interface BPSubject {

    void registerObserver(BPObserver bpObserver);

    void removeObserver(BPObserver bpObserver);

    void notifyItemInserted(Object obj);

    void notifyItemChanged(Object obj);

    void notifyItemRemoved(Object obj);

}
