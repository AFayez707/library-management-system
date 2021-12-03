package dataaccess;

public class DataAccessProvider {

    private static DataAccess dataAccess = null;
    public static DataAccess getInstance()
    {
        if (dataAccess == null)
            dataAccess = new DataAccessFacade();
        return dataAccess;
    }
}
