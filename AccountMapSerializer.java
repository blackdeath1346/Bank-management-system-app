import java.io.*;

public class AccountMapSerializer {

    // Method to serialize an AccountMap object to a file
    public static void serializeAccountMap(AccountMap accountMap, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(accountMap);
        }
    }

    // Method to deserialize an AccountMap object from a file
    public static AccountMap deserializeAccountMap(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (AccountMap) ois.readObject();
        }
    }
    public static void main(String[] args) {
        String fileName = "accountMap.ser";
        AccountMap accountMap = new AccountMap();

        accountMap.addAccount(1, 500.0, "password1");
        accountMap.addAccount(2, 1000.0, "password2");

        try {
            serializeAccountMap(accountMap, fileName);

            AccountMap deserializedAccountMap = deserializeAccountMap(fileName);

            deserializedAccountMap.displayAccount(1);
            deserializedAccountMap.displayAccount(2);

            deserializedAccountMap.withdrawFromAccount(1, 100.0, "password1");
            deserializedAccountMap.transferMoney(2,1,200,"password2");
            serializeAccountMap(deserializedAccountMap, fileName);
            

            AccountMap finalAccountMap = deserializeAccountMap(fileName);
            finalAccountMap.displayAccount(1);
            finalAccountMap.displayAccount(2);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}