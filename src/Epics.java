import java.util.HashMap;
import java.util.Objects;

public class Epics {
   private HashMap<Integer,Epic> epics;
    public Epics(){
        epics=new HashMap<>();
    }

    public void addEpic(int id,Epic epic){
        epics.put(id,epic);
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }



    public void deleteAllEpics(){
        epics.clear();
    }




}
