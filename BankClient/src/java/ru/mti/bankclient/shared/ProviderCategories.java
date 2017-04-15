
package ru.mti.bankclient.shared;

/**
 * Категории поставщиков услуг
 * @author Евгений Белых
 */
public enum ProviderCategories {
    CELL_PHONE(1, "Сотовая связь"),
    INTERNET(2, "Интернет"),
    UTILITIES(3, "ЖКХ");
    
    private int id;
    private String name;
    
    private ProviderCategories(int id, String name) {
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
