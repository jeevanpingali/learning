package pingali.jeevan.akka.async_sequencing.messages;

public class SecondSystemMessage {
    private Status status;

    public SecondSystemMessage(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return (status == Status.FIRST_SYSTEM_SUCCESS)?"Calling Second System only for option#1...":"Calling Second System for option#2...";
    }
}
