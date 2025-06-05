package itk_test.wallet;

import itk_test.model.Wallet;
import itk_test.model.WalletRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final WalletRowMapper walletRowMapper;

    public WalletRepository(JdbcTemplate jdbcTemplate, WalletRowMapper walletRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.walletRowMapper = walletRowMapper;
    }


    public Optional<Wallet> getWalletBalance(UUID walletUuid) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("""
                            SELECT * FROM wallets WHERE id = CAST(? AS TEXT)""",
                    walletRowMapper, walletUuid));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void increaseWalletBalance(UUID walletUuid, Long amount) {
        jdbcTemplate.update("""
                UPDATE wallets SET balance = balance + ? WHERE id = CAST(? AS TEXT)""", amount, walletUuid);
    }

    public void decreaseWalletBalance(UUID walletUuid, Long amount) {
        jdbcTemplate.update("""
                UPDATE wallets SET balance = balance - ? WHERE id = CAST(? AS TEXT)""", amount, walletUuid);
    }

    public Wallet addNewWallet(Long amount) {
        String newWalletUuid = insert("""
                INSERT INTO wallets (id, balance) VALUES (uuid_generate_v4(), ?)""", amount);
        return new Wallet(UUID.fromString(newWalletUuid), amount);
    }

    protected String insert(String query, Object... params) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                ps.setObject(idx + 1, params[idx]);
            }
            return ps;
        }, keyHolder);
        String id = (String)keyHolder.getKeys().get("id");
        if (id != null) {
            return id;
        } else {
            throw new RuntimeException("Не удалось сохранить данные");
        }
    }
}
