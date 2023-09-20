package ru.clevertec.cleverbank.constants;

/**
 * Класс, содержащий константы
 */
public class Constants {

    public static final String BANK_FIND_BY_ID_SQL =
            "SELECT * FROM bank WHERE id=?";
    public static final String BANK_FIND_ALL_SQL =
            "SELECT * FROM bank";
    public static final String BANK_CREATURE_SQL =
            "INSERT INTO bank (id, name, date_creation, last_modified, version) VALUES (?, ?, NOW(), NOW(), 1)";
    public static final String BANK_UPDATE_SQL =
            "UPDATE bank SET name=?, last_modified=NOW(), version=? WHERE id=?";
    public static final String BANK_DELETE_BY_ID_SQL =
            "DELETE FROM bank WHERE id=?";

    public static final String CLIENT_FIND_BY_ID_SQL =
            "SELECT * FROM client WHERE id=?";
    public static final String CLIENT_FIND_BY_ACCOUNT_NUMBER_SQL =
            "SELECT * FROM client c INNER JOIN bank_client_account_relation bca ON c.id=bca.client_id INNER JOIN account a ON bca.account_id=a.id WHERE a.number=?";
    public static final String CLIENT_FIND_ALL_SQL =
            "SELECT * FROM client";
    public static final String CLIENT_CREATURE_SQL =
            "INSERT INTO client (id, name, last_name, date_creation, last_modified, version) VALUES (?, ?, ?, NOW(), NOW(), 1)";
    public static final String CLIENT_UPDATE_SQL =
            "UPDATE client SET name=?, last_name=?, last_modified=NOW(), version=? WHERE id=?";
    public static final String CLIENT_DELETE_BY_ID_SQL =
            "DELETE FROM client WHERE id=?";

    public static final String ACCOUNT_FIND_BY_ID_SQL =
            "SELECT * FROM account WHERE id=?";
    public static final String ACCOUNT_FIND_BY_NUMBER_SQL =
            "SELECT * FROM account WHERE number=?";
    public static final String ACCOUNT_FIND_ALL_SQL =
            "SELECT * FROM account";
    public static final String ACCOUNT_FIND_BANK_NAME_BY_NUMBER_SQL =
            "SELECT b.name FROM bank b INNER JOIN bank_client_account_relation bca ON b.id=bca.bank_id INNER JOIN account a ON bca.account_id=a.id WHERE a.number=?";
    public static final String ACCOUNT_CREATURE_SQL =
            "INSERT INTO account (id, number, amount, currency, date_creation, last_modified, version) VALUES (?, ?, ?, ?, NOW(), NOW(), 1)";
    public static final String ACCOUNT_UPDATE_SQL =
            "UPDATE account SET number=?, amount=?, currency=?, last_modified=NOW(), version=? WHERE id=?";
    public static final String ACCOUNT_DELETE_BY_ID_SQL =
            "DELETE FROM account WHERE id=?";
    public static final String ACCOUNT_REAMOUNTING_SQL =
            "UPDATE account SET amount=?, last_modified=NOW(), version=? WHERE id=?";

    public static final String TRANSACTION_FIND_BY_ID_SQL =
            "SELECT * FROM transaction WHERE id=?";
    public static final String TRANSACTION_FIND_ALL_SQL =
            "SELECT * FROM transaction";
    public static final String TRANSACTION_CREATURE_SQL =
            "INSERT INTO transaction (id, account_number_from, account_number_to, amount, currency, date_creation, last_modified, version) VALUES (?, ?, ?, ?, ?, NOW(), NOW(), 1);" +
            "INSERT INTO transaction_account_relation (transaction_id, account_from_id, account_to_id) VALUES (?, ?, ?)";
    public static final String TRANSACTION_UPDATE_SQL =
            "UPDATE transaction SET account_number_from=?, account_number_to=?, amount=?, currency=?, last_modified=NOW(), version=? WHERE id=?;" +
            "UPDATE transaction_account_relation SET account_from_id=?, account_to_id=? WHERE transaction_id=?";
    public static final String TRANSACTION_DELETE_BY_ID_SQL =
            "DELETE FROM transaction WHERE id=?";
}
