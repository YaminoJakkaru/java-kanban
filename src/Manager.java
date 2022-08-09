public class Manager {
    Epics epics= new Epics();
    Tasks tasks= new Tasks();
     int IdentificationNumber =0;

     public String getAll(){
       return   getTasks() + "\n" + getEpics();
     }

     public void deleteAll(){
         deleteAllEpics();
         deleteAllTasks();
     }

     public void deleteById(int id){
         deleteEpic(id);
         deleteSubtask(id);
         deleteTask(id);
     }
     public String getInfoById(int id){
         String info=null;
         if(tasks.getTasks().containsKey(id))
             info = getTask(id);
         else if(epics.getEpics().containsKey(id))
             info=getEpic(id);
         else
             info=getSubtask(id);
         return info;
     }


    public void addTask(Task task){
        tasks.addTask(++IdentificationNumber, task);
        task.setIdentificationNumber(IdentificationNumber);
    }

    public void addSubtask(int id,Subtask subtask){
        epics.getEpics().get(id).addSubtask( ++IdentificationNumber, subtask);
        subtask.setIdentificationNumber(IdentificationNumber);
        subtask.setEpicId(id);
    }

    public void addEpic(Epic epic){
        epics.addEpic(++IdentificationNumber, epic);
        epic.setIdentificationNumber(IdentificationNumber);
        if(epic.getSubtasks().size()!=0) {
            updateEpicStatus(epic.getIdentificationNumber());
        }
    }

    public String getTasks() {
        String info="";

            for(Task task:tasks.getTasks().values()) {
            info= info + task.toString() + "\n";
            }
        return  info;
    }
    public String getSubtasks(int id) {
        String info="";

        for(Subtask subtask:  epics.getEpics().get(id).getSubtasks().values()) {
            info = info + subtask.toString() + "\n";
        }
        return  info;
    }
    public String getEpics(){
        String info="";

        for(Epic epic:epics.getEpics().values()) {
            info= info + epic.toString() + "\n"+getSubtasks(epic.getIdentificationNumber()) + "\n";
        }
        return  info;
    }


    public void deleteAllTasks(){
        tasks.deleteAllTasks();
    }

    public void deleteAllSubtasks(int id){
        epics.getEpics().get(id).deleteAllSubtasks();
    }

    public void deleteAllEpics(){
        epics.deleteAllEpics();
    }

    public String getTask(int id){
        return tasks.getTasks().get(id).toString() + "\n";
    }

    public String getEpic(int id){
        return epics.getEpics().get(id).toString() + "\n" + getSubtasks(id) + "\n";
    }

    public String getSubtask(int id){
         String info="";
        for(Epic epic:epics.getEpics().values()) {
            if( epic.getSubtasks().containsKey(id)) {
                info= epic.getSubtasks().get(id).toString() + "\n";
            }
        }
        return info;
    }

    public void updateTask(int id,Task task) {
        if(tasks.getTasks().containsKey(id)) {
            if(!tasks.getTasks().get(id).getName().equals(task.getName())) {
               tasks.getTasks().get(id).setName(task.getName());
            }
            if(!tasks.getTasks().get(id).getDescription().equals(task.getDescription())) {
               tasks.getTasks().get(id).setDescription(task.getDescription());
            }
            if(!tasks.getTasks().get(id).getStatus().equals(task.getStatus())) {
               tasks.getTasks().get(id).setStatus(task.getStatus());
            }
        }
    }

    public  void updateEpic(int id ,Epic epic) {
        if(epics.getEpics().containsKey(id)) {
            if(!epics.getEpics().get(id).getName().equals(epic.getName())) {
                epics.getEpics().get(id).setName(epic.getName());
            }
            if(!epics.getEpics().get(id).getDescription().equals(epic.getDescription())) {
                epics.getEpics().get(id).setDescription(epic.getDescription());
            }
            if(!epics.getEpics().get(id).getStatus().equals(epic.getStatus())) {
                epics.getEpics().get(id).setStatus(epic.getStatus());
            }
            updateEpicStatus(id);
        }

    }

    public void updateSubtask(int id,Subtask subtask) {
        for(Epic epic:epics.getEpics().values()) {
            if (epic.getSubtasks().containsKey(id)) {
                if(!epic.getSubtasks().get(id).getName().equals(subtask.getName())) {
                    epic.getSubtasks().get(id).setName(subtask.getName());
                }
                if(!epic.getSubtasks().get(id).getDescription().equals(subtask.getDescription())) {
                    epic.getSubtasks().get(id).setDescription(subtask.getDescription());
                }
                if(!epic.getSubtasks().get(id).getStatus().equals(subtask.getStatus())) {
                    epic.getSubtasks().get(id).setStatus(subtask.getStatus());
                }
                updateEpicStatus(epic.getIdentificationNumber());

                return;
            }
        }
    }

    public  void updateEpicStatus(int id) {
        int doneSubtasks=0;

        for(Subtask subtask: epics.getEpics().get(id).getSubtasks().values()) {
            if(!subtask.getStatus().equals("NEW")){
                epics.getEpics().get(id).setStatus("IN_PROGRESS");
            }
            if(subtask.getStatus().equals("DONE")){
                doneSubtasks++;
            }
        }
        if(doneSubtasks == epics.getEpics().get(id).getSubtasks().size()){
            epics.getEpics().get(id).setStatus("DONE");
        }
    }

    public void deleteTask(int id) {
         if(tasks.getTasks().containsKey(id))
       tasks.getTasks().remove(id);
    }

    public void deleteEpic(int id){
         if(epics.getEpics().containsKey(id))
             epics.getEpics().remove(id);

    }

    public void deleteSubtask(int id){
        for(Epic epic:epics.getEpics().values()) {
            if (epic.getSubtasks().containsKey(id)) {
                epic.getSubtasks().remove(id);
                updateEpicStatus( epic.getIdentificationNumber());
                return;
            }
        }
    }


}
