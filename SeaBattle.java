package LearnToCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.*;


/*********************************************************/

public class SeaBattle {

    private GameHelper helper = new GameHelper(); //объявляем и инициализируем переменные, которые нам понадобятся
    private ArrayList<DotCom> dotComList = new ArrayList<DotCom>();
    private int numOfGuess = 0;

    //---------------------------------------------------------//
    private void setUpGame(){
        //создаем несколько "сайтов" и даем им имена"

        //создаем три объекта DotCom6 даем им имена и помещаем в ArrayList
        DotCom one = new DotCom();
        one.setName("Artour12");
        DotCom two = new DotCom();
        one.setName("KenyS123");
        DotCom three = new DotCom();
        one.setName("Ruslan12");

        dotComList.add(one);
        dotComList.add(two);
        dotComList.add(three);
        //

        //Выводим краткие инструкции для пользователя
        System.out.println("Ваша цель - поразить три объекта");
        System.out.println("Arthur, Kenny, Ruslan");
        System.out.println("Попытайтесь потопить их за минимальное количество ходов!");
        //


        for(DotCom dotComToSet : dotComList) {      //повторяем с каждым обхектом dotcom в списке
            ArrayList<String> newLocation = helper.placeDotCom(3); //запрашиваем у вспомогательного объекта адрес "сайта"
            dotComToSet.setLocationCells(newLocation); // вызываем сеттер из объекта ДотКом, чтобы передать ему местопо
            //ложение, которое только что получили от вспомогательного объекта
        }

    } // конец метода СетАпГейм
    //---------------------------------------------------------//

    //---------------------------------------------------------//
    private void StartPlaying() throws IOException {
        while(!dotComList.isEmpty()){  // до тех поп, пока список обхектов ДотКом не станет пустым
            String userGuess = helper.getUserInput("Сделайте ход: ");  //Получаем пользовательский ввод
            checkUserGuess(userGuess); //Вызываем метод ЧекЮзерГес
        }
        finishGame(); //Вызываем метод ФинишГейм
    } // конец метода СтартПлеинг
    //---------------------------------------------------------//

    //---------------------------------------------------------//
    private void checkUserGuess(String userGuess){

        numOfGuess ++; // инкрементиурем кол-во попыток, которое сделал игрок
        String result = "Мимо"; //подразумеваме промах, пока не выясним обратное

        for(DotCom dotComToTest : dotComList){   //повторяем для всех обхектов ДотКом в списке
            result = dotComToTest.checkYourSelf(userGuess); //просим ДотКом проверить зод пользователя, ищем попадание(или потопление)
            if (result.equals("Попал")) {

                break;  //выбираемся из цикла раньше времени, нет смысла проверять другие сайты
            }
            if(result.equals("Потопил")){
                dotComList.remove(dotComToTest); //ему пришел конец, удаляем из списка и выходим из цикла
                break;
            }
        } // конец for

        System.out.println(result); //выводим для пользователя результат
    } //конец метода
    //---------------------------------------------------------//


    //---------------------------------------------------------//
    private void finishGame(){
        System.out.println("Все объекты поражены! Миссия выполнена!");

        if(numOfGuess <= 18){ // выводим сообщение о том, как пользователь прошел игру
            System.out.println("Это заняло у вас всего " + numOfGuess + " попыток!");

        } else{
            System.out.println("Это заняло у вас довольно много времени. " + numOfGuess + " попыток!");

        }
    } // конец метода
    //---------------------------------------------------------//

    //---------------------------------------------------------//
    public static void main(String[] args) throws IOException{
        SeaBattle game = new SeaBattle(); //создаем игровой объект
        game.setUpGame(); //говорим игровому объекту подготовить игру
        game.StartPlaying(); //говорим игровому объекту начать главный игровой цикл
    }
}
    //---------------------------------------------------------//

/*********************************************************/


/*********************************************************/

class DotCom{

    private ArrayList<String> locationCells;
    private String name;

    //---------------------------------------------------------//
    public void setLocationCells(ArrayList<String> loc){
        locationCells = loc;
    }
    //---------------------------------------------------------//

    //---------------------------------------------------------//
    public void setName(String n){

            name = n;
    }
    //---------------------------------------------------------//


    //---------------------------------------------------------//
    public String checkYourSelf(String userInput){

        String result = "Мимо";
        int index = locationCells.indexOf(userInput);

        if(index >= 0){
            locationCells.remove(index);

            if(locationCells.isEmpty()){
                result = "Потопил";
                System.out.println("Ой! Вы уничтожили " + name + " *sadface*");
            } else{
                result = "Попал";
            }
        }

        return result;

    }
    //---------------------------------------------------------//
}

/*********************************************************/

/*********************************************************/

class GameHelper{

    private static final String alphabet = "abcdefg";
    private int gridLength = 7;
    private int gridSize = 49;
    private int[] grid = new int[gridSize];
    private int comCount = 0;

    //---------------------------------------------------------//
    public String getUserInput(String prompt) throws IOException{
        String inputLine = null;
        System.out.println(prompt + " ");

        try{

            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();

            if(inputLine.length() == 0) return null;

        } catch (IOException e){
            System.out.println("IOException: " + e);
        }

        return inputLine.toLowerCase();
    }
    //---------------------------------------------------------//


    //---------------------------------------------------------//
    public ArrayList<String> placeDotCom(int comSize){
        ArrayList<String> alphaCells = new ArrayList<String>();
        String[] alphacoords = new String[comSize];
        String temp = null;
        int[] coords = new int[comSize];
        int attempts = 0;
        boolean success = false;
        int location = 0;

        comCount++;
        int incr = 1;

        if((comCount%2) == 1){
            incr = gridLength;
        }

        while( !success & attempts++ <200){
            location = (int) (Math.random()*gridSize);
            int x = 0;
            success = true;

            while(success && x < comSize){
                if(grid[location] == 0){

                    coords[x++] = location;
                    location += incr;

                    if(location >= gridSize){
                        success = false;
                    }
                    if(x>0 && ( location % gridLength == 0)){
                        success = false;
                    }
                } else {
                    success = false;
                }
            }
        }

        int x = 0;
        int row = 0;
        int column = 0;

        while(x < comSize){
            grid[coords[x]] = 1;
            row = (int) (coords[x] / gridLength);
            column = coords[x] % gridLength;
            temp = String.valueOf(alphabet.charAt(column));

            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;


        }

        return alphaCells;

    }
    //---------------------------------------------------------//
}

/*********************************************************/
