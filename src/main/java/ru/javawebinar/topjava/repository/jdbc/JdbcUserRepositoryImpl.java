package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

	private static final ResultSetExtractor<List<User>> RESULT_SET_EXTRACTOR = rs -> {
		Map<Integer, User> usersMap = new HashMap<>();
		LinkedList userResult = new LinkedList();
		while (rs.next()) {
			if (!usersMap.containsKey(rs.getInt("id"))) {
				HashSet<Role> user_roles = new HashSet<>();
				user_roles.add(Role.valueOf(rs.getString("role")));
				usersMap.putIfAbsent(rs.getInt("id"),
						new User(rs.getInt("id"),
								rs.getString("name"),
								rs.getString("email"),
								rs.getString("password"),
								rs.getInt("calories_per_day"),
								rs.getBoolean("enabled"),
								user_roles));
				userResult.add(usersMap.get(rs.getInt("id")));
			} else {
				usersMap.get(rs.getInt("id")).getRoles().add(Role.valueOf(rs.getString("role")));
			}
		}
		return userResult;
	};

	private final JdbcTemplate jdbcTemplate;

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final SimpleJdbcInsert insertUser;

	@Autowired
	public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.insertUser = new SimpleJdbcInsert(dataSource)
				.withTableName("users")
				.usingGeneratedKeyColumns("id");

		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public User save(User user) {
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
		if (user.isNew()) {
			Number newKey = insertUser.executeAndReturnKey(parameterSource);
			user.setId(newKey.intValue());
			String sql = "INSERT INTO user_roles (user_id,role) VALUES (?, ?)";
			List<Role> user_roles = new ArrayList<>(user.getRoles());
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Role role = user_roles.get(i);
					ps.setInt(1, user.getId());
					ps.setString(2, role.name());
				}

				@Override
				public int getBatchSize() {
					return user_roles.size();
				}
			});
		} else {
			namedParameterJdbcTemplate.update(
					"UPDATE users SET name=:name, email=:email, password=:password, " +
							"registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
		}
		return user;
	}

	@Override
	@Transactional
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
	}

	@Override
	public User get(int id) {
		List<User> users = jdbcTemplate.query("SELECT users.*,user_roles.role FROM users,user_roles WHERE id=? AND users.id=user_roles.user_id", RESULT_SET_EXTRACTOR, id);
		return DataAccessUtils.singleResult(users);
	}

	@Override
	public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
		List<User> users = jdbcTemplate.query("SELECT * FROM users,user_roles WHERE email=? AND users.id=user_roles.user_id", RESULT_SET_EXTRACTOR, email);
		return DataAccessUtils.singleResult(users);
	}

	@Override
	public List<User> getAll() {
		return jdbcTemplate.query("SELECT * FROM users,user_roles WHERE users.id=user_roles.user_id ORDER BY name, email", RESULT_SET_EXTRACTOR);
	}
}
