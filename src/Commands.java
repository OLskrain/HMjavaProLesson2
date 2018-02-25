
public interface Commands {
    String PRICE = "/price";
    String CHANGE_PRICE = "/change_price";
    String GOODS_BY_PRICE = "/goods_by_price";
    String END = "/end";
    String SHOW_COMMANDS = "/commands?";
    String[] com ={
            PRICE + " - узнать цену на товар.",
            CHANGE_PRICE + " - изменить цену на товар.",
            GOODS_BY_PRICE + " - показать товар из диапазона.",
            END + " - выход.",
            SHOW_COMMANDS + " - показать все команды."};
}
