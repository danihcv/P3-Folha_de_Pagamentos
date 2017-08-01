import java.util.Date;

public class DataEHora {
    public DataEHora(){}

    public Date getTimeDifference(Date dateBegin, Date dateEnd){
        return new Date(dateEnd.getTime() - dateBegin.getTime());
    }
}