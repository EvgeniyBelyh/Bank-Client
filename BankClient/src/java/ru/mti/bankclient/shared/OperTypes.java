
package ru.mti.bankclient.shared;

/**
 * Типы операций
 * @author Евгений Белых
 */
public enum OperTypes {
    TRANSFER_IN(1, "Внутренний перевод"),
    TRANSFER_OUT(2, "Внешний перевод"),
    SERVICE_PAY(3, "Оплата услуг");
    
    private int id;
    private String name;
    
    private OperTypes(int id, String name) {
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
