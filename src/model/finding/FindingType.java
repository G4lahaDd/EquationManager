package model.finding;

/**
 * Перечисление типов поиска, хранящий экземпляр метода поиска
 *
 * @author Kazyro I.A.
 * @version 1.0
 */
public enum FindingType {
    HALVING("Деление пополам", new HalvingMethod()),
    CHORD("Метод хорд", new ChordMethod());

    /**
     * Имя метода
     */
    private final String name;
    /**
     * Метод поиска
     */
    private final FindingMethod method;

    /**
     * Конструктор класса
     * @param name имя метода поиска
     * @param method метод поиска
     */
    FindingType(String name, FindingMethod method){
        this.name = name;
        this.method = method;
    }

    /**
     * Возвращает обьект метода поиска
     */
    public FindingMethod getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return name;
    }
}
