package main.java.listener;

public interface CrudListener {

    void prePersist(Object horcrux);

    void preRemove(Object horcrux);

    void postLoad(Object horcrux);
}
