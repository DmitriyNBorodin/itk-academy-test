package itk_test.model;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class WalletRowMapper implements RowMapper<Wallet> {
    @Override
    public Wallet mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Wallet(
                UUID.fromString(resultSet.getString("id")),
                resultSet.getLong("balance")
        );
    }
}
