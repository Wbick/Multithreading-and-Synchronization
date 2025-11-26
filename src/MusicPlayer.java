import java.io.File;

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
                if(allowPlay == 0 || allowPlay == 1){
                    System.out.println("Thread1: "+note);
                    sound.play("sounds/"+note+".wav");
                }else{
                    while(allowPlay == 2){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                //switch to other thread for those notes
                if(note.equals("re") ||
                    note.equals("fa")||
                    note.equals("la")){
                        allowPlay = 2;
                    continue;
                }else if(note.equals("do-octave"))allowPlay = 0;
            }
            
        }


    } 

    //re, fa, la, do-octave
    public static class Thread2 extends Thread{
        
        @Override
        public void run(){
            String[] instructions = musicSheet.split(" ");
            for(String note : instructions){
                if(allowPlay == 0 || allowPlay == 2){
                    System.out.println("Thread2: "+note);
                    sound.play("sounds/"+note+".wav");
                }else{
                    while(allowPlay == 1){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                //switch to other thread for those notes
                if(note.equals("do") ||
                    note.equals("mi")||
                    note.equals("sol")||
                    note.equals("si")){
                        allowPlay = 1;
                    continue;
                }else if(note.equals("do-octave"))allowPlay = 0;
            }
        }
    }

    public static void main(String[] args){

        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();

        musicSheet = "do re mi fa sol la si do-octave";

        allowPlay = 1;

        t1.start();
        t2.start();

    }
}