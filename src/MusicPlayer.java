import java.io.File;

public class MusicPlayer{
    private static int allowPlay = 1;
    private static String musicSheet;
    private static boolean skipNote = false; 
    private static boolean play;
    static FilePlayer sound = new FilePlayer();

    //do, mi, sol, si, do-octave
    public static class Thread1 extends Thread{

        @Override
        public void run(){
            String[] instructions = musicSheet.split(" ");
            for(String note : instructions){
                //delay start of note
                try {
                    sleep(300);
                } catch (InterruptedException e){}
                //switch to other thread for those notes
                if(note.equals("re") ||
                    note.equals("fa")||
                    note.equals("la")){
                        skipNote=false;
                        allowPlay = 2;
                }else if(note.equals("do-octave"))
                    allowPlay = 0;
                //silent note
                else if(note.equals("|")){
                     allowPlay = -1;
                    skipNote = true;
                }
                else{
                    allowPlay =1;
                    skipNote = true;
                }
                //playing sound
                if(allowPlay == 0 || allowPlay == 1){
                    System.out.println("Thread1: "+note);
                    sound.play("sounds/"+note+".wav");
                }
                    
                try {
                    sleep(300);
                } catch (InterruptedException e){}
                }
                play = false;
            }
            
        }


    

    //re, fa, la, do-octave
    public static class Thread2 extends Thread{
        
        @Override
        public void run(){
            String[] instructions = musicSheet.split(" ");
            int noteNumber =-1;
            //this thread is contantly looping
            while(play) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
            }
                //playing note
                if(allowPlay == 0 || allowPlay == 2){
                    String note = instructions[++noteNumber];
                    System.out.println("Thread2: "+note);
                    sound.play("sounds/"+note+".wav");
                    allowPlay = -1;
            }
                //Thread 1 plays a note
                else if(skipNote) {
                    ++noteNumber;
                    skipNote = false;
                }
                
        }
        return;
        }
    }

    public static void main(String[] args){

        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();

        musicSheet = "do re mi fa sol la si do-octave";

        play = true;
        t1.start();
        t2.start();
        
        while(play){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            } 
        }
        t1.stop();
        t2.stop();
        Thread t3 = new Thread1();
        Thread t4 = new Thread2();
     
        System.out.println("Twinkle Twinkle Little Star");
        musicSheet = "do do so | so | la la so | fa fa mi mi re re do so | so | fa fa mi mi re so | so | fa fa mi mi re do do so | so | la la so | fa fa mi mi re re do";
        play = true;
        t3.start();
        t4.start();
    }
}