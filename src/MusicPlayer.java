public class MusicPlayer{
    private static int allowPlay = 1;
    private static String musicSheet;
    
    static FilePlayer sound = new FilePlayer();

    //do, mi, sol, si, do-octave
    public static class Thread1 extends Thread{

        @Override
        public void run(){
            String[] instructions = musicSheet.split(" ");
            for(String note : instructions){
                if(note.equals("re") ||
                    note.equals("fa")||
                    note.equals("la"))
                    {
                    System.out.println("Switching to thread 2");
                    synchronized(this){
                        allowPlay = 2;
                    }
                    continue;
                    
                }else if(note.equals("do-octave"))allowPlay = 0;

                //delayes unitl threads turn
                while(allowPlay == 2){System.out.print("");}

                //normal notes case
                if(allowPlay == 1){
                    //delay note sound
                    try {
                        sleep(500);
                        sound.play("sounds/"+note+".wav");
                        System.out.println("Thread1: "+note);
                    } catch (Exception e) {}


                //do-octave case
                }else if(allowPlay == 0){
                    synchronized(this){
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {}
                        sound.play("sounds/"+note+".wav");
                        System.out.println("Thread1: "+note);
                        break;
                    }   
                }
            }
        }

    } 

    //re, fa, la, do-octave
    public static class Thread2 extends Thread{
        
        @Override
        public void run(){
            String[] instructions = musicSheet.split(" ");
            for(String note : instructions){
                if(note.equals("do") ||
                    note.equals("mi")||
                    note.equals("sol")||
                    note.equals("si"))
                    {
                    System.out.println("Switching to thread 1");
                    synchronized(this){
                        allowPlay = 1;
                    }
                    continue;
                    
                }else if(note.equals("do-octave"))allowPlay = 0;

                //delayes unitl threads turn
                while(allowPlay == 1){System.out.print("");}

                //normal notes case
                if(allowPlay == 2){
                    //delay note sound
                    try {
                        sleep(500);
                        sound.play("sounds/"+note+".wav");
                        System.out.println("Thread2: "+note);
                    } catch (Exception e) {}


                //do-octave case
                }else if(allowPlay == 0){
                    synchronized(this){
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {}
                        sound.play("sounds/"+note+".wav");
                        System.out.println("Thread2: "+note);
                        break;
                    }                    
                }
            }
        }
    }

    public static void main(String[] args){

        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();

        //musicSheet = "do do sol sol la la sol fa fa mi mi re re do sol sol fa fa mi mi re sol sol fa fa mi mi re do do sol sol la la sol fa fa mi mi re re do";
        musicSheet = "do re mi fa sol la si do-octave";
        allowPlay = 1;

        t1.start();
        t2.start();
        
        
    }
}