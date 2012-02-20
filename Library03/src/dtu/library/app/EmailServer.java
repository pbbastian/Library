package dtu.library.app;

public class EmailServer {
    public void send(String email, String subject, String text) {
        System.out.println("Mail sent to "+email+". Subject: \""+subject+"\", text: \""+text+"\"");
    }
}
