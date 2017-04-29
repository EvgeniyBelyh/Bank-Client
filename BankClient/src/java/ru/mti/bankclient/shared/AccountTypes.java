
package ru.mti.bankclient.shared;

/**
 * Типы счетов
 * @author Белых Евгений
 */
public enum AccountTypes {

    CREDIT_CARD(1, "Кредитная карта"),
    DEBIT_CARD(2, "Дебетовая карта"),
    DEPOSIT(3, "Вклад");
    
    private int id;
    private String name;
    
    private AccountTypes(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static AccountTypes getById(int id) {
        switch(id) {
            case 1:
                return CREDIT_CARD;
            case 2:
                return DEBIT_CARD;
            case 3:
                return DEPOSIT;
        }
        return null;
    }
}
