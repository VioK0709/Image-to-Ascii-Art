public class Main {

    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Converter();
        GServer server = new GServer(converter);
        server.start();

        String url = "https://sun1-23.userapi.com/impf/EvbjfkKGNyO46aHf1hx-SygDOYnRnfLwdhfo9Q/Pad7Z4WFe28.jpg?size=50x0&quality=96&crop=81,81,486,486&sign=dcb752f172c29c8fa6f05d942593c33b&ava=1";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);
    }
}