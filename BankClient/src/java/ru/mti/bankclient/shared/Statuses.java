
package ru.mti.bankclient.shared;

/**
 * Статусы
 * @author Евгений Белых
 */
public enum Statuses {
    NEW(1, "Новый"),
    EXECUTED(2, "Исполнено"),
    NOT_EXECUTED(3, "Не исполнено");
    
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
