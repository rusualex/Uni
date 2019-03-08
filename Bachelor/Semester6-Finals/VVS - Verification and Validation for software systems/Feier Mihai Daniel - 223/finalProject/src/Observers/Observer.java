package Observers;

import Utils.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
