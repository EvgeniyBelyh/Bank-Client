
package ru.mti.bankclient.shared;

/**
 * Статусы
 * @author Евгений Белых
 */
public enum Statuses {
    NEW(1, "Новый"),
    ON_EXECUTION(2, "На исполнении"),
    EXECUTED(3, "Исполнен"),
    NOT_EXECUTED(4, "Не исполнен");
    
    private int id;
    private String name;
    
    private Statuses(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
