
package ru.mti.bankclient.shared;

/**
 * Типы счетов
 * @author Белых Евгений
 */
public enum AccountTypes {

    CREDIT_CARD(1),
    DEBIT_CARD(2),
    DEPOSIT(3);
    
    private int id;
    
    private AccountTypes(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
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
