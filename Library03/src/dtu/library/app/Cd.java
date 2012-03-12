package dtu.library.app;

public class Cd extends Medium {
    public Cd(String signature, String title, String author) {
        super(signature, title, author);
    }

    @Override
    public int getMaxDaysForLoan() {
        return 7;
    }

    @Override
    public int getFine() {
        return 40;
    }
}
